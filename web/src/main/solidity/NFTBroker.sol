// SPDX-License-Identifier: MIT
// OpenZeppelin Contracts (last updated v4.5.0) (token/ERC1155/presets/ERC1155PresetMinterPauser.sol)

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC1155/ERC1155.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Burnable.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Pausable.sol";
import "@openzeppelin/contracts/access/AccessControlEnumerable.sol";
import "@openzeppelin/contracts/utils/Context.sol";

/**
 * @dev {ERC1155} token, including:
 *
 *  - ability for holders to burn (destroy) their tokens
 *  - a minter role that allows for token minting (creation)
 *  - a pauser role that allows to stop all token transfers
 *
 * This contract uses {AccessControl} to lock permissioned functions using the
 * different roles - head to its documentation for details.
 *
 * The account that deploys the contract will be granted the minter and pauser
 * roles, as well as the default admin role, which will let it grant both minter
 * and pauser roles to other accounts.
 *
 * _Deprecated in favor of https://wizard.openzeppelin.com/[Contracts Wizard]._
 */
contract NFTBroker is Context, AccessControlEnumerable, ERC1155Pausable {
    event Mint(address indexed to, uint256 indexed id, uint256 amount, bytes32 nft_hash, bytes data);
    event BatchMint(address[] indexed to, uint256[] indexed ids, uint256[] amounts, bytes32[] nft_hash, bytes data);
    event Transfer(address indexed from, address indexed to, uint256 id, uint256 amount, bytes data);
    event BatchTransfer(address indexed from, address[] indexed to, uint256[] ids, uint256[] amounts, bytes data);
    event Burn(uint256 id);
    event BatchBurn(uint256[] ids);
    event Record(uint256 orderID, bytes __orderInfo);

    address private _owner;

    struct nft_fingerprint {
        bytes32 hash;
        bool isUsed;
    }
    // Mapping from token ID to hash(data fingerprint)
    mapping(uint256 => nft_fingerprint) private _fingerprint;

    // Mapping from id to is destroyed
    mapping(uint256 => bool) private _isDestroyed;

    struct orderInfo {
        bytes info;
        bool isUsed;
    }

    // Mapping from orderID to specific info
    mapping(uint256 => orderInfo) private _orderInfo;

    bytes32 public constant MINTER_ROLE = keccak256("MINTER_ROLE");
    bytes32 public constant PAUSER_ROLE = keccak256("PAUSER_ROLE");

    /**
     * @dev Grants `DEFAULT_ADMIN_ROLE`, `MINTER_ROLE`, and `PAUSER_ROLE` to the account that
     * deploys the contract.
     */
    constructor(string memory uri) ERC1155(uri) {
        _setupRole(DEFAULT_ADMIN_ROLE, _msgSender());

        _setupRole(MINTER_ROLE, _msgSender());
        _setupRole(PAUSER_ROLE, _msgSender());

        _owner = _msgSender();
    }

    /**
     * @dev Creates `amount` new tokens for `to`, of token type `id`.
     *
     * See {ERC1155-_mint}.
     *
     * Requirements:
     *
     * - the caller must have the `MINTER_ROLE`.
     */
    function mint(
        address to,
        uint256 id,
        uint256 amount,
        bool need_validate_nft_hash,
        bytes32 nft_hash,
        bytes memory data
    ) public virtual {
        require(hasRole(MINTER_ROLE, _msgSender()), "NFTBroker: must have minter role to mint");
        require(_isDestroyed[id] == false, "NFTBroker: cannot mint a destroyed token");
        if (!need_validate_nft_hash || !_fingerprint[id].isUsed) {
            _fingerprint[id] = nft_fingerprint({
            hash : nft_hash,
            isUsed : true
            });
        } else {
            require(keccak256(abi.encodePacked(_fingerprint[id].hash)) == keccak256(abi.encodePacked(nft_hash)), "nft_hash is inconsistent compared to the record on the blockchain");
        }
        _mint(to, id, amount, data);
        emit Mint(to, id, amount, nft_hash, data);
    }

    function mintBatch(
        address[] memory to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bool need_validate_nft_hash,
        bytes32[] memory nft_hash,
        bytes memory data
    ) public virtual {
        require(hasRole(MINTER_ROLE, _msgSender()), "NFTBroker: must have minter role to mint");
        require(ids.length == amounts.length &&
        ids.length == to.length &&
            ids.length == nft_hash.length, "NFTBroker: inconsistent length of input");
        require(ids.length != 0, "NFTBroker: input cannot be empty");
        for (uint256 i = 0; i < ids.length; ++i) {
            require(_isDestroyed[ids[i]] == false, "NFTBroker: cannot mint a destroyed token");
            if (!need_validate_nft_hash || !_fingerprint[ids[i]].isUsed) {
                _fingerprint[ids[i]] = nft_fingerprint({
                hash : nft_hash[i],
                isUsed : true
                });
            } else {
                require(keccak256(abi.encodePacked(_fingerprint[ids[i]].hash)) == keccak256(abi.encodePacked(nft_hash)), "nft_hash is inconsistent compared to the record on the blockchain");
            }
            _mint(to[i], ids[i], amounts[i], data);
        }
        emit BatchMint(to, ids, amounts, nft_hash, data);
    }

    /** 
    * @dev return the fingerprint of a token 
    */
    function queryFingerprint(uint256 id) public view virtual returns (bytes32) {
        require(!_isDestroyed[id], "NFTBroker: cannot query a destroyed token");
        return _fingerprint[id].hash;
    }

    function transfer(
        address from,
        address to,
        uint256 id,
        uint256 amount,
        uint256 orderID,
        bytes memory __orderInfo,
        bytes memory data
    ) public virtual {
        require(_orderInfo[orderID].isUsed == false, "NFTBroker: orderID already exists");
        require(_isDestroyed[id] == false, "NFTBroker: cannot transfer a destroyed token");
        _orderInfo[orderID] = orderInfo({
        info : __orderInfo,
        isUsed : true
        });
        safeTransferFrom(from, to, id, amount, data);
        emit Transfer(from, to, id, amount, data);
    }

    function transferBatchRevertAll(address from,
        address[] memory to,
        uint256[] memory ids,
        uint256[] memory amounts,
        uint256[] memory orderIDs,
        bytes[] memory orderInfos,
        bytes memory data
    ) public virtual {
        require(orderIDs.length == orderInfos.length &&
        to.length == ids.length &&
        to.length == orderIDs.length &&
            to.length == amounts.length, "NFTBroker: inconsistent length of input");
        require(orderIDs.length != 0, "NFTBroker: empty input!");
        for (uint256 i = 0; i < orderIDs.length; ++i) {
            require(_orderInfo[orderIDs[i]].isUsed == false, "NFTBroker: orderID already exists");
            require(_isDestroyed[ids[i]] == false, "NFTBroker: the order have been destroyed");
            _orderInfo[orderIDs[i]] = orderInfo({
            info : orderInfos[i],
            isUsed : true
            });
            safeTransferFrom(from, to[i], ids[i], amounts[i], data);
        }
        emit BatchTransfer(from, to, ids, amounts, data);
    }

    function strConcat(string memory _a, string memory _b) internal virtual returns (string memory) {
        bytes memory _ba = bytes(_a);
        bytes memory _bb = bytes(_b);
        string memory ret = new string(_ba.length + _bb.length);
        bytes memory bret = bytes(ret);
        uint k = 0;
        for (uint256 i = 0; i < _ba.length; ++i) bret[k++] = _ba[i];
        for (uint256 i = 0; i < _bb.length; ++i) bret[k++] = _bb[i];
        return string(ret);
    }

    function transferBatchRevertFailure(address from,
        address[] memory to,
        uint256[] memory ids,
        uint256[] memory amounts,
        uint256[] memory orderIDs,
        bytes[] memory orderInfos,
        bytes memory data
    ) public virtual returns (string memory) {
        require(orderIDs.length == orderInfos.length &&
        to.length == ids.length &&
        to.length == orderIDs.length &&
            to.length == amounts.length, "NFTBroker: inconsistent length of input");
        require(orderIDs.length != 0, "NFTBroker: empty input!");
        string memory log;
        for (uint256 i = 0; i < orderIDs.length; ++i) {
            if (_orderInfo[orderIDs[i]].isUsed == true) {
                log = strConcat(log, "||NFTBroker: orderID already exists");
                continue;
            }
            if (_isDestroyed[ids[i]] == true) {
                log = strConcat(log, "||NFTBroker: the order have been destroyed");
                continue;
            }
            if (to[i] == address(0)) {
                log = strConcat(log, "||NFTBroker: transfer to the zero address");
                continue;
            }
            if (balanceOf(from, ids[i]) < amounts[i]) {
                log = strConcat(log, "||NFTBroker: insufficient balance in account");
                continue;
            }
            log = strConcat(log, "||");
            _orderInfo[orderIDs[i]] = orderInfo({
            info : orderInfos[i],
            isUsed : true
            });
            safeTransferFrom(from, to[i], ids[i], amounts[i], data);
        }
        emit BatchTransfer(from, to, ids, amounts, data);
        return log;
    }

    function burn(
        uint256 id
    ) public virtual {
        require(
            _msgSender() == _owner,
                "NFTBroker: caller is not the contract owner"
        );
        _isDestroyed[id] = true;

        emit Burn(id);
    }

    function burnBatch(
        uint256[] memory ids
    ) public virtual {
        require(
            _msgSender() == _owner,
                "NFTBroker: caller is not the contract owner"
        );

        for (uint256 i = 0; i < ids.length; i++) {
            _isDestroyed[ids[i]] = true;
        }

        emit BatchBurn(ids);
    }

    // record the orderInfo from generated 
    function recordOrder(uint256 orderID, bytes memory __orderInfo) public virtual {
        require(_msgSender() == _owner, "NFTBroker: caller is not the contract owner");
        require(_orderInfo[orderID].isUsed == false, "NFTBroker: orderID already exists");
        _orderInfo[orderID] = orderInfo({
        info : __orderInfo,
        isUsed : true
        });
        emit Record(orderID, __orderInfo);
    }

    // query orderInfo by orderIDs
    function queryOrderInfo(uint256 orderID) public view virtual returns (bytes memory) {
        return _orderInfo[orderID].info;
    }

    function isDestroyed(uint256 id) public view virtual returns (bool) {
        return _isDestroyed[id];
    }

    /**
     * @dev Pauses all token transfers.
     *
     * See {ERC1155Pausable} and {Pausable-_pause}.
     *
     * Requirements:
     *
     * - the caller must have the `PAUSER_ROLE`.
     */
    function pause() public virtual {
        require(hasRole(PAUSER_ROLE, _msgSender()), "NFTBroker: must have pauser role to pause");
        _pause();
    }

    /**
     * @dev Unpauses all token transfers.
     *
     * See {ERC1155Pausable} and {Pausable-_unpause}.
     *
     * Requirements:
     *
     * - the caller must have the `PAUSER_ROLE`.
     */
    function unpause() public virtual {
        require(hasRole(PAUSER_ROLE, _msgSender()), "NFTBroker: must have pauser role to unpause");
        _unpause();
    }

    /**
     * @dev See {IERC165-supportsInterface}.
     */
    function supportsInterface(bytes4 interfaceId)
    public
    view
    virtual
    override(AccessControlEnumerable, ERC1155)
    returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }

    function _beforeTokenTransfer(
        address operator,
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) internal virtual override(ERC1155Pausable) {
        super._beforeTokenTransfer(operator, from, to, ids, amounts, data);
    }
}
