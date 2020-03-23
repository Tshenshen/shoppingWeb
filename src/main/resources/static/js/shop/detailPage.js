new Vue({
    el: "#app",
    data: {
        shopVo: null,
        goodsImgUrlList: [""],
        selectedItemIndex: -1,
        defaultItem: {
            name: '',
            price: '',
            stock: 1
        },
        currentItem: {},
        shopItemNum: ''
    },
    mounted() {
        var _that = this;
        var shopId = _that.$refs.shopId.getAttribute("value");
        axios.get("getShopVo/" + shopId).then(function (value) {
            if (value.data.success) {
                _that.shopVo = value.data.content;
                if (_that.shopVo !== null) {
                    _that.setImageList();
                    _that.defaultItem.name = _that.shopVo.name;
                    _that.defaultItem.price = _that.shopVo.price;
                    _that.currentItem = _that.defaultItem;
                    document.querySelector("#title").innerHTML = _that.defaultItem.name;
                }
            } else {
                _that.$message.error(value.data.message);
            }
        }).catch(function (reason) {
            console.log(reason);
            _that.$message.error("初始化店铺信息错误！");
        });
    },
    computed: {
        itemClass() {
            var _that = this;
            return function (index) {
                if (_that.selectedItemIndex === index) {
                    return "item selected-item";
                } else {
                    return "item";
                }
            }
        },
        hasStock() {
            var _that = this;
            if (_that.selectedItemIndex === -1) {
                return false;
            } else {
                return _that.currentItem.stock > 0;
            }
        },
        itemSelected() {
            return this.selectedItemIndex !== -1;
        }
    },

    methods: {
        cancelCollectShop() {
            var _that = this;
            axios({
                method: "delete",
                url: "/ShopWeb/user/cancelCollectShop",
                data: {
                    id: _that.shopVo.collect.id
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.$message.success(value.data.message);
                    _that.shopVo.collect = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("取消收藏错误！");
            });
        },
        collectShop() {
            var _that = this;
            axios({
                method: "post",
                url: "/ShopWeb/user/collectShop",
                data: {
                    shopId: _that.shopVo.id
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.shopVo.collect = value.data.content;
                    _that.$message.success(value.data.message);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("收藏店铺错误！");
            });
        },
        setImageList() {
            var _that = this;
            var imageList = [];
            if (_that.shopVo.images.length > 0) {
                _that.shopVo.images.split(",").forEach(function (image) {
                    imageList.push("/ShopWeb/image/" + _that.shopVo.id + "/" + image);
                });
            } else {
                imageList.push("")
            }
            _that.goodsImgUrlList = imageList;
        },
        selectItem(event, index) {
            var _that = this;
            _that.shopItemNum = 1;
            if (_that.selectedItemIndex === -1) {
                _that.selectedItemIndex = index;
                _that.currentItem = _that.shopVo.shopItems[index];
            } else if (_that.selectedItemIndex === index) {
                _that.selectedItemIndex = -1;
                _that.currentItem = _that.defaultItem;
            } else {
                _that.selectedItemIndex = index;
                _that.currentItem = _that.shopVo.shopItems[index];
            }
        },
        addGoods() {
            var _that = this;
            axios({
                method: "post",
                url: "/ShopWeb/cart/addGoods",
                data: {
                    shopItemNum: _that.shopItemNum,
                    shopItemId: _that.currentItem.id
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.$message.success(value.data.message);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error('添加购物车错误！！');
            });

        },
        buySingleGoods() {
            var _that = this;
            axios({
                method: "post",
                url: "/ShopWeb/cart/buySingleGoods",
                data: {
                    shopItemNum: _that.shopItemNum,
                    shopItemId: _that.currentItem.id
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.$refs.selectedList.setAttribute("value", value.data.content);
                    _that.$refs.selectedListForm.submit();
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error('立即购买错误！！');
            });
        }
    }
});