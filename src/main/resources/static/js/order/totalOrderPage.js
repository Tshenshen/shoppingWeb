new Vue({
    el: "#app",
    data: {
        type: '',
        state: '1',
        orderList: [],
        total: 0,
        pageNum:1
    },
    mounted() {
        var _that = this;
        _that.type = _that.$refs.type.getAttribute("value");
        if (_that.type === 'enterprise') {
            _that.state = '2';
        }
        _that.getOrderVoListByOrderInfo();
    },
    computed: {
        picUrl() {
            return function (shopVo) {
                return shopVo.images.length > 0 ? "/ShopWeb/image/" + shopVo.id + "/" + shopVo.images.split(",")[0] : "";
            }
        },
        orderDetailUrl() {
            return function (id) {
                return "/ShopWeb/order/orderDetail/" + id;
            }
        }
    },
    methods: {
        getOrderVoListByOrderInfo() {
            var _that = this;
            axios({
                method: "get",
                url: "/ShopWeb/order/getOrderVoListByOrderInfo",
                params: {
                    type: _that.type,
                    state: _that.state,
                    pageNum: _that.pageNum
                }
            }).then(function (value) {
                if (value.data.success) {
                    _that.orderList = value.data.content.list;
                    _that.total = value.data.content.total;
                } else {
                    _that.$message.error(value.data.message)
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("获取订单列表错误！！")
            })
        },
        changeTab(){
            var _that = this;
            _that.pageNum = 1;
            _that.getOrderVoListByOrderInfo();
        },
        toPayPage(id) {
            this.$refs.orderId.setAttribute("value", id);
            this.$refs.toPayPageForm.submit();
        },
        orderCancel(index) {
            var _that = this;
            _that.$confirm('是否确认取消订单?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "delete",
                    url: "/ShopWeb/order/cancelById/" + _that.orderList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.$message.success(value.data.message);
                        _that.getOrderVoListByOrderInfo();
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("取消订单错误！")
                })
            }).catch(function (reason) {
                console.log(reason)
            });
        },
        orderReceive(index) {
            var _that = this;
            _that.$confirm('是否确认收货?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/receiveById/" + _that.orderList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.$message.success(value.data.message);
                        _that.getOrderVoListByOrderInfo();
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("确认收货错误！")
                })
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        orderRefundApply(index) {
            var _that = this;
            _that.$prompt('退款原因', '退款申请', {
                confirmButtonText: '退款申请',
                cancelButtonText: '取消',
                inputValidator: function (value) {
                    if (value === null) {
                        return "请输入退款原因";
                    } else if (value.length > 100) {
                        return "最长100个字"
                    } else if (value.length === 0) {
                        return "请输入退款原因";
                    } else if (value.length > 100) {
                        return "最长100个字"
                    } else {
                        return true;
                    }
                }
            }).then(function (val) {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/refundApply",
                    data: {
                        id: _that.orderList[index].id,
                        refundReason: val.value
                    }
                }).then(function (value) {
                    if (value.data.success) {
                        _that.$message.success(value.data.message);
                        _that.getOrderVoListByOrderInfo();
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("订单申请退款错误！")
                })
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        orderSend(index) {
            var _that = this;
            _that.$confirm('是否确认发货?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/sendById/" + _that.orderList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.$message.success(value.data.message);
                        _that.getOrderVoListByOrderInfo();
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("确认发货错误！")
                })
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        refundHandle(index) {
            var _that = this;
            _that.$confirm("退款原因：" + _that.orderList[index].refundReason, '退款处理', {
                confirmButtonText: '确认退款',
                cancelButtonText: '拒绝退款',
                distinguishCancelAndClose:true,
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/refundApply/" + _that.orderList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.$message.success(value.data.message);
                        _that.getOrderVoListByOrderInfo();
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("确认退款错误！")
                })
            }).catch(function (reason) {
                if (reason === "cancel") {
                    axios({
                        method: "put",
                        url: "/ShopWeb/order/refundRefuse/" + _that.orderList.refundList[index].id
                    }).then(function (value) {
                        if (value.data.success) {
                            _that.$message.success(value.data.message);
                            _that.getOrderVoListByOrderInfo();
                        } else {
                            _that.$message.error(value.data.message);
                        }
                    }).catch(function (reason) {
                        console.log(reason);
                        _that.$message.error("拒绝退款错误！")
                    })
                }
            });
        }
    }
});