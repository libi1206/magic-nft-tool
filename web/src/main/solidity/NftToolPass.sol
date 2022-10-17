// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "@openzeppelin/contracts/access/Ownable.sol";


contract NftToolPass is ERC721, ERC721Enumerable, ERC721URIStorage, Ownable {

    using Counters for Counters.Counter;
    Counters.Counter private _tokenId;

    /**
     *  我们在构造方法中实现了设定该合约生成的NFT的名称的设定、简写的设定
     */
    constructor() ERC721("Nft Tool Pass", "NTP") {}

    /**
     *  为_recipient地址铸造一个NFT，并将其元数据URL设置为_tokenUrl
     */
    function mint(address _recipient, string memory _tokenUrl) public onlyOwner returns (uint _mintTokenId){
        require(bytes(_tokenUrl).length > 0, "The _tokenUrl must be have");
        _tokenId.increment();
        uint newTokenId = _tokenId.current();
        _mint(_recipient, newTokenId);
        _setTokenURI(newTokenId, _tokenUrl);
        return newTokenId;
    }

    /**
     *  销毁一个NFT
     */
    function burn(uint memory _tokenId) public onlyOwner returns (uint _bornedTokenId){
        _burn(_tokenId);
        return _tokenId;
    }


    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
    internal
    override(ERC721, ERC721Enumerable)
    {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function _burn(uint256 tokenId) internal override(ERC721, ERC721URIStorage) {
        super._burn(tokenId);
    }

    /**
     *  获取指定NFT的URL信息
     */
    function tokenURI(uint256 tokenId)
    public
    view
    override(ERC721, ERC721URIStorage)
    returns (string memory)
    {
        return super.tokenURI(tokenId);
    }

    function supportsInterface(bytes4 interfaceId)
    public
    view
    override(ERC721, ERC721Enumerable)
    returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }
}