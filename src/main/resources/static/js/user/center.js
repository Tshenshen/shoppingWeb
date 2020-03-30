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
            orderList: {
                unPayList: [],
                unSendList: [],
                unReceiveList: [],
                refundList: []
            },
            collectShopNumber: 0,
            rechargeDialogVisible: false,
            payType: '测试',
            rechargeForm: {
                balance: ""
            },
            rules: {
                balance: [
                    {required: true, message: "充值金额不能为空！", trigger: "blur"},
                    {validator: validatePrice, trigger: "blur"}
                ]
            }
        }
    },
    mounted() {
        var _that = this;
        axios.get("getCollectShopNumber").then(function (value) {
            if (value.data.success) {
                _that.collectShopNumber = value.data.content;
            } else {
                _that.$message.error(value.data.message)
            }
        }).catch(function (reason) {
            console.log(reason);
            _that.$message.error("获取收藏数量错误！！")
        });
        axios.get(ctx + "/order/getUnFinishOrders").then(function (value) {
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
    }
    ,
    computed: {
        picUrl() {
            return function (shopVo) {
                return shopVo.images.length > 0 ? ctx + "/image/" + shopVo.id + "/" + shopVo.images.split(",")[0] : "";
            }
        }
        ,
        orderDetailUrl() {
            return function (id) {
                return ctx + "/order/orderDetail/" + id;
            }
        }
    }
    ,
    methods: {
        submitRechargeForm() {
            var _that = this;
            _that.rechargeDialogVisible = false;
            axios({
                method: "put",
                url: "rechargeToWallet",
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
        formatToDouble() {
            this.rechargeForm.balance = this.fixToDouble(this.rechargeForm.balance)
        }
        ,
        fixToDouble(value) {
            value = value.replace(/(\-)/, "");
            value = value.replace(/^(0+)/gi, "");
            if (value.trim() === "") {
                return "";
            }
            var temp = value.split(".");
            temp[1] += "00";
            if (value.indexOf(".") > 0) {
                value = temp[0] + "." + temp[1].substr(0, 2);
            } else if (value.indexOf(".") === 0) {
                value = "0." + temp[1].substr(0, 2);
            } else {
                value += ".00";
            }
            return value;
        }
        ,
        toPayPage(id) {
            this.$refs.orderId.setAttribute("value", id);
            this.$refs.toPayPageForm.submit();
        }
        ,
        orderCancel(index) {
            var _that = this;
            _that.$confirm('是否确认取消订单?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "delete",
                    url: ctx + "/order/cancelById/" + _that.orderList.unPayList[index].id
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
        }
        ,
        orderRefundApply(listName, index) {
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
                    url: ctx + "/order/refundApply",
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
        }
        ,
        orderReceive(index) {
            var _that = this;
            _that.$confirm('是否确认收货?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                axios({
                    method: "put",
                    url: ctx + "/order/receiveById/" + _that.orderList.unReceiveList[index].id
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