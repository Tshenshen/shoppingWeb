new Vue({
    el: "#app",
    data: {
        createShopDialogVisible: false,
        createShopForm: {
            type: '',
            style: '',
            tagList:[]
        },
        updateShopDialogVisible: false,
        updateShopIndex: '',
        updateShopForm: {},
        rules: {
            name: [
                {required: true, message: "请输入店铺/商品名", trigger: 'blur'}
            ],
            type: [
                {required: true, message: "请选择商品种类", trigger: 'blur'}
            ],
            style: [
                {required: true, message: "请选择商品类型", trigger: 'blur'}
            ],
            price: [
                {required: true, message: "请输入商品价格区间", trigger: 'blur'}
            ],
            describe: [
                {required: true, message: "请输入商品描述", trigger: 'blur'}
            ]
        },
        typeDic: [],
        createFormStyleDic: [],
        updateFormStyleDic: [],
        tagDicList: [],
        selectTagIndex: '',
        shopList: [],
        imageList: []
    },
    mounted() {
        var _that = this;
        axios.get("getMyShopList").then(function (value) {
            if (value.data.success) {
                _that.shopList = value.data.content;
            } else {
                _that.$message.error(value.data.message);
            }
        }).catch(function (reason) {
            console.log(reason);
            _that.$message.error("获取店铺列表错误！")
        });
        axios.get("/ShopWeb/dictionary/getDictionaryListByRootValue?value=TYPE_DIC").then(function (value) {
            if (value.data.success) {
                _that.typeDic = value.data.content;
            } else {
                _that.$message.error(value.data.message);
            }
        }).catch(function (reason) {
            console.log(reason);
            _that.$message.error("获取种类错误！")
        })
    },
    computed: {
        shopImg() {
            return function (shopId, images) {
                if (images.length > 0) {
                    return "/ShopWeb/image/" + shopId + "/" + images.split(",")[0];
                } else {
                    return ""
                }
            }
        },
        toUrlWithImageId() {
            return function (shopId, imageId) {
                return "/ShopWeb/image/" + shopId + "/" + imageId;
            }
        },
        uploadShopImage() {
            return "uploadShopImage/" + this.updateShopForm.id
        }
    },
    methods: {
        addTagToUpdateForm(tagDic){
            var tag = {
                dicId:tagDic.id,
                name:tagDic.name
            };
            this.updateShopForm.tagList.push(tag);
            this.tagDicList=[]
        },
        addTagToCreateForm(tagDic){
            var tag = {
                dicId:tagDic.id,
                name:tagDic.name
            };
            this.createShopForm.tagList.push(tag);
            this.tagDicList=[]
        },
        getUpdateTagDicList(keyword){
            this.getTagDicList(this.updateShopForm , keyword)
        },
        getCreateTagDicList(keyword){
            this.getTagDicList(this.createShopForm , keyword)
        },
        getTagDicList(form, keyword) {
            var _that = this;
            axios({
                method: "get",
                url: "/ShopWeb/dictionary/getTagDictionaryListByStyleIdAndKeyWord",
                params: {
                    styleId: form.style,
                    keyWord: keyword
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.tagDicList = _that.removeSameTag(value.data.content,form.tagList);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("搜索标签列表错误！")
            })
        },
        removeSameTag(dist, diff){
            var newArr = [];
            var theSame;
            for(var i = 0; i < dist.length; i++){
                theSame = false;
                for(var j = 0; j < diff.length; j++){
                    if(dist[i].id === diff[j].dicId){
                        theSame = true;
                        break;
                    }
                }
                if (!theSame) {
                    newArr.push(dist[i]);
                }
            }
            return newArr;
        },
        submitCreateShopForm() {
            var _that = this;
            _that.$refs.createShopFormRef.validate(function (isValid) {
                if (isValid) {
                    _that.createShopDialogVisible = false;
                    axios({
                        method: "post",
                        url: "createNewShop",
                        data: _that.createShopForm
                    }).then(function (value) {
                        if (value.data.success) {
                            _that.shopList.push(value.data.content);
                            _that.$message.success(value.data.message);
                        } else {
                            _that.$message.error(value.data.message);
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                        _that.$message.error("新建店铺错误！")
                    })
                } else {
                    _that.$message.error("请完善店铺信息！")
                }
            })
        },
        createFormSelectType() {
            this.createShopForm.style = '';
            this.updateShopForm.tagList = [];
            this.getStyleDic(this.createShopForm.type, "createFormStyleDic")
        },
        updateFormSelectType() {
            this.updateShopForm.style = '';
            this.updateShopForm.tagList = [];
            this.getStyleDic(this.updateShopForm.type, "updateFormStyleDic")
        },
        getStyleDic(type, styleDic) {
            var _that = this;
            axios({
                method: "get",
                url: "/ShopWeb/dictionary/getDictionaryListByParentId",
                params: {
                    parentId: type
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that[styleDic] = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取类型错误！")
            })
        },
        deleteShopById(index) {
            var _that = this;
            _that.$confirm('是否确认删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "delete",
                    url: "deleteShopById/" + _that.shopList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.shopList.splice(index, 1);
                        _that.$message.success(value.data.message);
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("删除店铺错误！")
                })
            }).catch(function (reason) {
                console.log(reason)
            });

        },
        updateShopEnable(index) {
            var _that = this;
            axios({
                method: "put",
                url: "updateShopEnable",
                data: {
                    id: _that.shopList[index].id,
                    enable: _that.shopList[index].enable
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.$message.success(value.data.message);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("开启或关闭店铺错误！")
            })
        },
        openUpdateShopDialog(index) {
            var _that = this;
            _that.updateShopIndex = index;
            _that.imageList = [];
            _that.updateShopForm = JSON.parse(JSON.stringify(_that.shopList[index]));
            if (_that.updateShopForm.images.length > 0) {
                var urls = _that.updateShopForm.images.split(",");
                urls.forEach(function (value) {
                    var image = {name: value, url: "/ShopWeb/image/" + _that.updateShopForm.id + "/" + value};
                    _that.imageList.push(image);
                });
            }
            _that.getStyleDic(_that.updateShopForm.type, "updateFormStyleDic");
            _that.updateShopDialogVisible = true;
        },
        submitUpdateShopForm() {
            var _that = this;
            _that.$refs.updateShopFormRef.validate(function (isValid) {
                if (isValid) {
                    _that.updateShopDialogVisible = false;
                    axios({
                        method: "put",
                        url: "updateShopInfo",
                        data: _that.updateShopForm
                    }).then(function (value) {
                        if (value.data.success) {
                            _that.shopList.splice(_that.updateShopIndex, 1, value.data.content);
                            _that.$message.success(value.data.message);
                        } else {
                            _that.$message.error(value.data.message);
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                        _that.$message.error("修改店铺信息错误！")
                    })
                } else {
                    _that.$message.error("请完善店铺信息！")
                }
            })
        },
        removeImage(file, fileList) {
            var _that = this;
            axios({
                method: "delete",
                url: "deleteShopImage/" + _that.updateShopForm.id,
                params: {
                    imageName: file.name
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.imageList = fileList;
                    _that.updateShopForm.images = value.data.content;
                    _that.shopList[_that.updateShopIndex].images = value.data.content;
                    _that.$message.success(value.data.message);
                } else {
                    _that.imageList.splice(0, 0);
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("删除图片错误！")
            })
        },
        uploadImageSuccess(data, file, fileList) {
            var _that = this;
            console.log(data);
            if (data.success) {
                _that.updateShopForm.images = data.content;
                _that.shopList[_that.updateShopIndex].images = data.content;
                _that.$message.success(data.message);
                _that.imageList = fileList;
                let index = data.content.lastIndexOf(",");
                if (index < 0) {
                    file.name = data.content
                } else {
                    file.name = data.content.substr(index + 1);
                }
            } else {
                _that.$message.error(data.message);
            }
        }
    }
});