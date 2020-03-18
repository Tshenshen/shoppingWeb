new Vue({
    el: "#app",
    data() {
        var validatePrice = function (rule, value, callback) {
            var pattern = /\d+.\d\d$/;
            if (!pattern.test(value)) {
                return callback(new Error("请输入数字"))
            } else {
                return callback()
            }
        };
        return {
            shopVo: {},
            goodsImgUrlList: [""],
            selectedItemIndex: -1,
            defaultItem: {
                id: '',
                shopId: '',
                name: '',
                price: '',
                stock: 1
            },
            currentItem: {},
            goodsNum: '',
            createDialogVisible: false,
            createShopItemForm: {
                shopId: '',
                name: '',
                price: '0.00',
                stock: ''
            },
            updateDialogVisible: false,
            updateShopItemFormIndex: '',
            updateShopItemForm: {},
            rules: {
                name: [
                    {required: true, message: "请输入商品名称", trigger: "blur"},
                    {max: 30, message: "商品名称最长30个字", trigger: "blur"}
                ],
                price: [
                    {required: true, message: "请输入商品价格", trigger: "blur"}
                    , {validator: validatePrice, trigger: "blur"}
                ],
                stock: [
                    {type: "number", required: true, message: "请输入商品库存", trigger: "blur"}
                ]
            }

        }
    },
    mounted() {
        var _that = this;
        var shopId = _that.$refs.shopId.getAttribute("value");
        _that.createShopItemForm.shopId = shopId;
        axios.get("getShopVo/" + shopId).then(function (value) {
            if (value.data.success) {
                _that.shopVo = value.data.content;
                var imageList = [];
                _that.shopVo.images.split(",").forEach(function (image) {
                    imageList.push("/ShopWeb/image/" + _that.shopVo.id + "/" + image);
                });
                _that.goodsImgUrlList = imageList;
                _that.defaultItem.name = _that.shopVo.name;
                _that.defaultItem.price = _that.shopVo.price;
                _that.currentItem = _that.defaultItem;
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
        itemSelected(){
            return this.selectedItemIndex !== -1;
        }
    },

    methods: {
        selectItem(event, index) {
            var _that = this;
            _that.goodsNum = 1;
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
        fixToDouble() {
            var value = this.createShopItemForm.price;
            var temp = value.split(".");
            temp[1] += "00";
            if (value.indexOf(".") > 0) {
                value = temp[0] + "." + temp[1].substr(0, 2);
            } else if (value.indexOf(".") === 0) {
                value = "0." + temp[1].substr(0, 2);
            } else {
                value += ".00";
            }
            this.createShopItemForm.price = value;
        },
        fixToDouble2() {
            var value = this.updateShopItemForm.price;
            var temp = value.split(".");
            temp[1] += "00";
            if (value.indexOf(".") > 0) {
                value = temp[0] + "." + temp[1].substr(0, 2);
            } else if (value.indexOf(".") === 0) {
                value = "0." + temp[1].substr(0, 2);
            } else {
                value += ".00";
            }
            this.updateShopItemForm.price = value;
        },
        submitCreateShopItemForm() {
            var _that = this;
            _that.$refs.createShopItemFormRef.validate(function (isValid) {
                if (isValid) {
                    _that.createDialogVisible = false;
                    axios({
                        method: "post",
                        url: "addNewShopItem",
                        data: _that.createShopItemForm
                    }).then(function (value) {
                        if (value.data.success) {
                            _that.shopVo.shopItems.push(value.data.content);
                            _that.$message.success(value.data.message);
                        } else {
                            _that.$message.error(value.data.message);
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                        _that.$message.error("添加新商品错误！");
                    });
                } else {
                    _that.$message.error("请完善商品信息！");
                }
            });
        },
        openUpdateDialog(index) {
            var _that = this;
            _that.updateShopItemForm = JSON.parse(JSON.stringify(_that.shopVo.shopItems[index]));
            _that.updateShopItemForm.price = _that.updateShopItemForm.price.toFixed(2);
            _that.updateShopItemFormIndex = index;
            _that.updateDialogVisible = true;
        },
        submitUpdateShopItemForm() {
            var _that = this;
            _that.$refs.updateShopItemFormRef.validate(function (isValid) {
                if (isValid) {
                    _that.updateDialogVisible = false;
                    axios({
                        method: "put",
                        url: "updateShopItem",
                        data: _that.updateShopItemForm
                    }).then(function (value) {
                        if (value.data.success) {
                            _that.shopVo.shopItems.splice(_that.updateShopItemFormIndex, 1, value.data.content);
                            _that.$message.success(value.data.message);
                        } else {
                            _that.$message.error(value.data.message);
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                        _that.$message.error("更新商品错误！");
                    });
                } else {
                    _that.$message.error("请完善商品信息！");
                }
            });
        },
        deleteShopItem(index) {
            var _that = this;
            _that.$confirm('是否确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "delete",
                    url: "deleteShopItem",
                    params: {
                        shopItemId: _that.shopVo.shopItems[index].id
                    }
                }).then(function (value) {
                    if (value.data.success) {
                        _that.shopVo.shopItems.splice(index, 1);
                        _that.$message.success(value.data.message);
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("删除商品错误！");
                });
            }).catch(function (reason) {
                console.log(reason)
            });
        }

    }
});