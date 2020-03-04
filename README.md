# 一、创建工程
## 1.建立框架
* 使用springBoot 2.2.4，maven
* 导入依赖
## 2.创建实体类，创建数据库
* 编写entity类
* 编写ddl创建数据库（tbl_ddl.sql）
## 3.测试数据库连接
* 编写test方法测试curd
## 4.集成mybatis
* 编写userMapper接口,添加注释 *@Mapper*
* 主类添加注释  *@MapperScan("com.liang.shoppingweb.mapper")*
* 编写test方法测试mapper
    
# 二、商品首页
## 1.用webScraper爬数据
* 为chrome浏览器添加插件（在xxxx文件夹中）
* 使用教程视屏也在
* 先抓点数据好干事
## 2.编写浏览商品页面
* 将抓到数据放入数据库
* 编写前端页面
* 编写对应的controller,mapper,service
* 整合pageHelper

# 三、加入登陆页
## 1.整合springSecurity
* 创建security配置类
* 继承*WebSecurityConfigurerAdapter*
* 编写授权逻辑，设置权限逻辑
* 初始化登陆，注销功能，记住我功能，指定跳转的链接
## 2.加入登陆界面
* 编写登陆界面
* 设置登陆链接
## 3.商品页面添加用户细节
* 商品页面显示用户信息
* 设置注销链接
## 4.添加注册页面
* 编写注册页面
* 测试用户测试功能
## 5.添加记录用户最后一次登录时间的功能
* 编写*MyLoginSuccessHandler* 继承*SavedRequestAwareAuthenticationSuccessHandler*
* 编写相应的mapper和service 
* 将MyLoginSuccessHandler注册到security的loginForm里边
```text
http.formLogin().loginPage("/userLogin").successHandler(myLoginSuccessHandler);
```

# 四、加入商品详细页面
* 引入element ui
* 编写商品详细页面
* 添加跳转链接

# 五、加入购物车页面
* 添加加入购物车功能
* 编写购物车页面
* 添加跳转链接

# 六、加入订单页面
* 添加购物车结算功能，进入订单页面
* 编写订单页面
* 添加跳转链接

# 七、编写个人主页
* 编写个人主页页面
* 添加链接
* 添加收货地址功能
* 添加订单查询功能

# 八、编写收货地址页面
* 编写收货地址页面
* 添加CRUD功能

# 九、编写订单详细页面
* 编写订单详细页面
* 添加修改收货地址功能
* 添加链接

# 十、添加商家模块
* 用户可注册为商家
* 商家可以开店铺（若干）
* 店铺中包含同类型的，不同规格的商品
* 创建商家表，店铺表，商品表拆分出规格表
* 编写我的店铺页面，注册商家页面，添加商品页面