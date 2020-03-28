new Vue({
    el: "#app",
    data: {
        orderList: {
            unSendList: [],
            unReceiveList: [],
            refundList: []
        },
        rechargeDialogVisible: false,
        payType: '测试',
        rechargeForm: {
            balance: ""
        },
        drawbackDialogVisible: false,
        drawbackForm: {
            balance: ""
        },
        rules: {
            balance: [
                {required: true, message: "充值金额不能为空！", trigger: "blur"},
                {type: "number", message: "请输入数字"}
            ]
        }
    },
    mounted() {
        var _that = this;
        axios.get("/ShopWeb/order/getUnFinishOrdersByEnterpriseId").then(function (value) {
            if (value.data.success) {
                value.data.content.forEach(function (order) {
                    if (order.state === 2) {
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
        submitRechargeForm() {
            var _that = this;
            _that.rechargeDialogVisible = false;
            axios({
                method: "put",
                url: "rechargeToWalletFromUser",
                data: _that.rechargeForm
            }).then(function (value) {
                if (value.data.success) {
                    _that.$refs.balance.innerHTML = "&yen;" + value.data.content.balance;
                    _that.$message.success(value.data.message);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("余额充值错误！")
            })
        },
        submitDrawbackForm() {
            var _that = this;
            _that.drawbackDialogVisible = false;
            axios({
                method: "put",
                url: "drawbackFromWalletToUser",
                data: _that.drawbackForm
            }).then(function (value) {
                if (value.data.success) {
                    _that.$refs.balance.innerHTML = "&yen;" + value.data.content.balance;
                    _that.$message.success(value.data.message);
                } else {
                    _that.$message.error(value.data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
                _that.$message.error("余额提现错误！")
            })
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
                    url: "/ShopWeb/order/sendById/" + _that.orderList.unSendList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.orderList.unSendList.splice(index, 1);
                        _that.orderList.unReceiveList.push(value.data.content);
                        _that.$message.success(value.data.message);
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
            _that.$confirm("退款原因：" + _that.orderList.refundList[index].refundReason, '退款处理', {
                confirmButtonText: '确认退款',
                cancelButtonText: '拒绝退款',
                distinguishCancelAndClose: true,
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: "/ShopWeb/order/refundApply/" + _that.orderList.refundList[index].id
                }).then(function (value) {
                    if (value.data.success) {
                        _that.orderList.refundList.splice(index, 1);
                        _that.$message.success(value.data.message);
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
                            _that.orderList.refundList.splice(index, 1);
                            _that.$message.success(value.data.message);
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