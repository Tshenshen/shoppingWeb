# 一、创建工程
## 1.建立框架
* 使用springboot 2.2.4，maven
* 导入依赖：  

## 2.创建实体类，创建数据库
* 编写entity类
* 编写ddl创建数据库（tbl_ddl.sql）
## 3.测试数据库连接
* 编写test方法测试curd
## 4.集成mybatis
* 编写usermapper接口,添加注释 *@Mapper*
* 主类添加注释  *@MapperScan("com.liang.shoppingweb.mapper")*
* 编写test方法测试mapper
## 5.用webscraper爬数据
* 为chrome浏览器添加插件（在xxxx文件夹中）
* 使用教程视屏也在
* 先抓点数据好干事
## 6.编写浏览商品页面
* 将抓到数据放入数据库
* 编写前端页面
* 编写对应的controller,mapper,service
* 整合pageHelper