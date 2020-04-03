new Vue({
    el: "#app",
    data: {
        shopSearchInfo: {
            type: '',
            style: '',
            keyword: '',
            pageNum: '1',
            tagList: []
        },
        keyword: '',
        curType: {},
        curStyle: {},
        typeDic: [],
        styleDic: [],
        paramTagDic: [],
        pageInfo: {
            list: []
        },
        loading: 0
    },
    mounted() {
        var _that = this;
        _that.pageChange(1);
        _that.getTypeDic();
    },
    computed: {
        isCurrentTag() {
            return function (id) {
                for (var i = 0; i < this.shopSearchInfo.tagList.length; i++) {
                    if (this.shopSearchInfo.tagList[i].dicId === id) {
                        return true;
                    }
                }
                return false
            }
        },
        imgUrl() {
            return function (shopId, images) {
                if (images.split(",")[0].length > 0) {
                    return ctx + "/image/" + shopId + "/" + images.split(",")[0];
                } else {
                    return ""
                }
            }
        }
    },
    methods: {
        addTag(tagDic, paramIndex) {
            var tag = {
                dicId: tagDic.id,
                name: tagDic.name
            };
            this.shopSearchInfo.tagList.splice(paramIndex, 1, tag);
            this.pageChange(1)
        },
        removeTag(index) {
            this.shopSearchInfo.tagList.splice(index, 1, {dicId: ''});
            this.pageChange(1)
        },
        pageChange(curPage) {
            var _that = this;
            _that.loading ++;
            _that.shopSearchInfo.pageNum = curPage;
            _that.getShopListBySearchInfo()
        },
        getShopListBySearchInfo() {
            var _that = this;
            _that.keyword = _that.shopSearchInfo.keyword;
            axios({
                method: "get",
                url: "getShopListBySearchInfo",
                params: {
                    shopSearchInfoStr: _that.getShopSearchInfoStr()
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.pageInfo = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取店铺列表错误！");
            }).finally(function () {
                _that.loading --;
            });
        },
        getShopSearchInfoStr() {
            var _that = this;
            var temp = JSON.parse(JSON.stringify(_that.shopSearchInfo));
            temp.tagList = [];
            _that.shopSearchInfo.tagList.forEach(function (value) {
                if (value.dicId) {
                    temp.tagList.push(value);
                }
            });
            return encodeURI(JSON.stringify(temp))
        },
        searchWithType(index) {
            if (index !== undefined) {
                this.curType = this.typeDic[index];
                this.shopSearchInfo.type = this.curType.id;
                this.getStyleDic();
            } else {
                this.shopSearchInfo.keyword = '';
                this.shopSearchInfo.tagList = [];
            }
            this.shopSearchInfo.style = '';
            this.pageChange(1)
        },
        searchWithStyle(index) {
            if (index !== undefined) {
                this.curStyle = this.styleDic[index];
                this.shopSearchInfo.style = this.curStyle.id;
                this.getParamTagDic();
            } else {
                this.shopSearchInfo.keyword = '';
                this.shopSearchInfo.tagList = [];
                for (var i = 0; i < this.paramTagDic.length; i++) {
                    this.shopSearchInfo.tagList.push({tag: {}})
                }
            }
            this.pageChange(1)
        },
        searchWithKeyword(keyword) {
            if (keyword !== undefined) {
                this.shopSearchInfo.keyword = keyword;
            }
            this.pageChange(1)
        },
        getTypeDic() {
            var _that = this;
            axios.get(ctx + "/dictionary/getDictionaryListByRootValue?value=TYPE_DIC").then(function (value) {
                if (value.data.success) {
                    _that.typeDic = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取种类字典错误！")
            })
        },
        getStyleDic() {
            var _that = this;
            axios({
                method: "get",
                url: ctx + "/dictionary/getDictionaryListByParentId",
                params: {
                    parentId: _that.shopSearchInfo.type
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.styleDic = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取类型字典错误！")
            })
        },
        getParamTagDic: function () {
            var _that = this;
            axios({
                method: "get",
                url: ctx + "/dictionary/getDictionaryVoListByParentId",
                params: {
                    parentId: _that.shopSearchInfo.style
                }
            }).then(function (value) {
                if (value.data.success) {
                    for (var i = 0; i < value.data.content.length; i++) {
                        _that.shopSearchInfo.tagList.push({dicId: ''})
                    }
                    _that.paramTagDic = value.data.content;
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取标签字典错误！")
            })
        }
    }

});