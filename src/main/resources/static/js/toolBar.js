const topNavBar = new Vue({
    el: "#topNavBar",
    data: {
        userInfo:{},
        isAuthenticated:false
    },
    mounted(){
        var _that = this
        _that.isAuthenticated = "true" === document.querySelector("#isAuthenticated").getAttribute("value");
        if (_that.isAuthenticated) {
            axios.get("/ShopWeb/getCurrentUser").then(function (response) {
                _that.userInfo = response.data;
                sessionStorage.setItem("isAuthenticated","true");
                sessionStorage.setItem("userInfo",JSON.stringify(_that.userInfo));
            }).catch(function () {
                // handle error
                _that.$message.error('获取用户信息失败！！');
            })
        }else {
            sessionStorage.setItem("isAuthenticated","false");
        }

    },
    methods: {
        logout() {
            var _that = this;
            axios.post("/ShopWeb/logout").then(function () {
                _that.$message.success("注销成功");
                console.log("注销成功");
            }).catch(function (reason) {
                _that.$message.error("注销失败");
                console.log("注销失败");
                console.log(reason);
            }).finally(function () {
                sessionStorage.removeItem("userInfo");
                sessionStorage.setItem("isAuthenticated","false");
                document.querySelector("#isAuthenticated").setAttribute("value", "false");
                _that.userInfo={};
                _that.isAuthenticated=false;
            })

        }
    }
})
