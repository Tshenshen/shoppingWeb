new Vue({
    el: "#app",
    data: {
        orderList: {
            unPayList: [],
            unSendList: [],
            unReceiveList: [],
            refundList: []
        }
    },
    mounted() {
        var _that = this;
        axios.get("/ShopWeb/order/getUnFinishOrders").then(function (value) {
            if (value.data.success) {
                value.data.content.forEach(function (order) {
                    if (order.state === 1) {
                        _that.orderList.unPayList.push(order);
                    } else if (order.state === 2) {
                        _that.orderList.unSendList.push(order);
                    } else if (order.state === 3) {
                        _that.orderList.unReceiveList.push(order);
                    } else if (order.state === 4) {
                        _that.orderList.refundList.push(order);
                    }
                })
            } else {
                _that.$message.error(value.data.message)
            }
        }).catch(function (reason) {
            console.log(reason);
            _that.$message.error("获取订单列表错误！！")
        })
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
                    url: "/ShopWeb/order/cancelById/" + _that.orderList.unPayList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.orderList.unPayList.splice(index, 1);
                        _that.$message.success(value.data.message);
                    } else {
                        _that.$message.error(value.data.message);
                    }
                }).catch(function (reason) {
                    console.log(reason);
                    _that.$message.error("取消订单错误！")
                })
            }).catch(function (reason) {

            });
        },
        orderRefundApply(listName, index) {
            var _that = this;
            _that.$prompt('退款原因', '退款申请', {
                confirmButtonText: '退款申请',
                cancelButtonText: '取消',
                inputValidator: function (value) {
                    if (value === null){
                        return "请输入退款原因";
                    }else if (value.length > 100) {
                        return "最长100个字"
                    }else if(value .length === 0) {
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
                        id: _that.orderList[listName][index].id,
                        refundReason: val.value
                    }
                }).then(function (value) {
                    if (value.data.success) {
                        _that.orderList[listName].splice(index, 1);
                        _that.orderList.refundList.push(value.data.content);
                        _that.$message.success(value.data.message);
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
        orderReceive(index){
            var _that = this;
            _that.$confirm('是否确认收货?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/receiveById/" + _that.orderList.unReceiveList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.orderList.unReceiveList.splice(index, 1);
                        _that.$message.success(value.data.message);
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
        }
    }
});