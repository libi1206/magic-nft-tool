// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "@openzeppelin/contracts/access/Ownable.sol";


contract NftToolPass is ERC721, ERC721URIStorage, Ownable {


    using Counters for Counters.Counter;
    /**
     * tokenId的计数器
     */
    Counters.Counter private _tokenId;
    /**
     * 当前token数量的计数器
     */
    Counters.Counter private _tokenNumCounter;

    /**
     * 需要打款的地址
     */
    address _targetAddress;

    /**
     * 白名单数组
     */
    address[] _whiteList;

    /**
     * NFT发行最大数量
     */
    uint _limitNumber;

    /**
     * 价格配置
     */
    struct SaleConfig {
        // 开始售卖时间
        uint32 publicSaleStartTime;
        // 售卖价格
        uint64 publicPriceWei;
        // 白名单开始售卖时间
        uint32 whitePublicSaleStartTime;
        // 白名单售卖价格
        uint64 whitePublicPriceWei;
    }

    /**
     *  我们在构造方法中实现了设定该合约生成的NFT的名称的设定、简写的设定
     *  设置打钱的账户，发行最大值
     */
    constructor(
        address targetAddress,
        uint limitNumber
    ) ERC721("Nft Tool Pass", "NTP") {
        _targetAddress = targetAddress;
        _whiteList = new address[](0);
        _limitNumber = limitNumber;
    }

    // ***************************** public方法Start **********************************************
    // Public Mint
    // 公共铸造方法
    function mint(string memory _tokenUrl)
    external
    payable
    callerIsUser
    {
        _checkAndAddLimit();
        uint256 priceWei;
        if (_senderIsWhite()){
            require(isWhitePublicSaleOn(), "Public sale has not begun yet");
            priceWei = saleConfig.whitePublicPriceWei;
        } else {
            require(isPublicSaleOn(), "Public sale has not begun yet");
            priceWei = saleConfig.publicPriceWei;
        }

        _tokenId.increment();
        uint _newTokenId = _tokenId.current();
        _safeMint(msg.sender, _newTokenId);
        _setTokenURI(_newTokenId, _tokenUrl);

        _refundIfOver(priceWei);
    }

    // 是否已经开售
    function isPublicSaleOn()
    public
    view
    returns(bool) {
        require(
            saleConfig.publicSaleStartTime != 0,
            "Public Sale Time is TBD."
        );

        return block.timestamp >= saleConfig.publicSaleStartTime;
    }

    // 白名单是否已经开售
    function isWhitePublicSaleOn()
    public
    view
    returns(bool) {
        require(
            saleConfig.whitePublicSaleStartTime() != 0,
            "Public White Sale Time is TBD."
        );

        return block.timestamp >= saleConfig.publicSaleStartTime;
    }

    // ***************************** public方法End **********************************************


    // ***************************** View方法Start **********************************************
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
    // ***************************** View方法End **********************************************

    // ***************************** 管理员方法Start **********************************************

    /**
     *  管理员的铸造方法
     *  为_recipient地址铸造一个NFT，并将其元数据URL设置为_tokenUrl
     */
    function mintAdmin(address _recipient, string memory _tokenUrl)
    public
    onlyOwner
    returns (uint _mintTokenId)
    {
        require(bytes(_tokenUrl).length > 0, "The _tokenUrl must be have");
        _checkAndAddLimit();
        _tokenId.increment();
        uint newTokenId = _tokenId.current();
        _mint(_recipient, newTokenId);
        _setTokenURI(newTokenId, _tokenUrl);
        return newTokenId;
    }

    /**
     *  销毁一个NFT
     */
    function burn(uint memory _tokenId)
    public
    onlyOwner
    returns (uint _bornedTokenId)
    {
        _tokenNumCounter.decrement();
        _burn(_tokenId);
        return _tokenId;
    }

    /**
     *  进行价格配置
     */
    function setupNonAuctionSaleInfo(
        uint64 publicPriceWei,
        uint32 publicSaleStartTime,
        uint64 whitePublicPriceWei,
        uint32 whitePublicSaleStartTime
    ) public onlyOwner {
        saleConfig = SaleConfig(
            publicSaleStartTime,
            publicPriceWei,
            whitePublicPriceWei,
            whitePublicSaleStartTime
        );
    }

    /**
     *  增加白名单
     */
    function whiteAdd(address newWhite)
    public
    onlyOwner{
        _whiteList.push(newWhite);
    }

    /**
     *  删除白名单
     */
    function whiteAdd(address delWhite)
    public
    onlyOwner{
        uint index;
        bool find = false;
        for(uint i=0; i< _whiteList.length; i++) {
            if(delWhite == _whiteList[i]){
                index = i;
                find = true;
                break;
            }
        }
        if(find){
            for (uint i = index; i<array.length-1; i++){
                array[i] = array[i+1];
            }
            delete array[array.length-1];
            array.length--;
        }
    }

    /**
     *  获取白名单
     */
    function getWhiteList()
    public
    view
    onlyOwner
    returns(address[]){
        return _whiteList;
    }

    /**
     *  设置打钱的账户
     */
    function setTargetAddress(address targetAddress)
    public onlyOwner {
        _targetAddress = targetAddress;
    }

    /**
     * 设置最大发行量
     */
    function setLimitNumber(uint limitNumber)
    public onlyOwner {
        _limitNumber = limitNumber;
    }
    // ***************************** 管理员方法End **********************************************


    function _beforeTokenTransfer(address from, address to, uint256 tokenId)
    internal
    override(ERC721)
    {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function _burn(uint256 tokenId) internal override(ERC721, ERC721URIStorage) {
        super._burn(tokenId);
    }

    /**
     * 余额检查并且返还
     * 并且将收款打入目标账号
     */
    function _refundIfOver(uint256 price) internal {
        require(msg.value >= price, "Need to send more ETH.");
        if (msg.value > price) {
            payable(msg.sender).transfer(msg.value - price);
        }
        payable(_targetAddress).transfer(price);
    }

    /**
     * 检查是否是白名单
     */
    function _senderIsWhite() internal returns (bool) {
        for(uint i=0; i< _whiteList.length; i++){
            msg.sender == _whiteList[i];
            return true;
        }
        return false;
    }

    /**
     * 检查是否超过最大发行量
     */
    function _checkAndAddLimit() internal {
        uint current = _tokenNumCounter.current();
        require(current < _limitNumber,"Reached maximum circulation");
        _tokenNumCounter.increment;
    }

    modifier callerIsUser() {
        require(tx.origin == msg.sender, "The caller is another contract");
        _;
    }
}