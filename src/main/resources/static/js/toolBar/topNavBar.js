
const topNavBar = new Vue({
    el: "#topNavBar",
    data: {
        isAuthenticated: "false"
    },
    mounted() {
        // console.log(ctxPath);
        this.isAuthenticated = (document.querySelector("#isAuthenticated").getAttribute("value").toString());
        sessionStorage.setItem("isAuthenticated", this.isAuthenticated);
    },
    methods: {
        logout() {
            var _that = this;
            axios.post(ctx + "/logout").then(function (value) {
                _that.$message.success(value.data.message);
                console.log(value.data.message);
            }).catch(function (reason) {
                _that.$message.error("注销失败");
                console.log("注销失败");
                console.log(reason);
            }).finally(function () {
                sessionStorage.setItem("isAuthenticated", "false");
                document.querySelector("#isAuthenticated").setAttribute("value", "false");
                _that.isAuthenticated = false;
            })

        }
    }
})
