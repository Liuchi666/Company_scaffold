# Company_scaffold
springboot+mybatis-plus进行后端开发的快捷脚手架

# @Auth 注解
## 1.简述
Auth 注解是一个提供用户身份校验功能的注解类，它的工作机制是在访问 Controller 时通过获取并解析请求头上的`Access-Token`来获取当前登陆用户的 Id，@Auth 注解允许传入一个 required 参数来限制身份验证的必须性。
## 2.如何使用？
###在必须验证身份的接口中
示例：
// 查询账户余额
@GetMapping("/money")
public Result getMoney(@Auth Long userId) {
    // 调用业务
}

如果访问该接口时请求头中的`Access-Token`不存在或者为空时，会抛出未登录的异常。
> 注意：@Auth 注解默认传出的参数为`Long`类型，如需更改类型，请在`注解参数解析器`源码中修改。
### 在非必须验证身份的接口中
示例：
// 获取当前用户的Id，如果用户未登录则结果为null
@GetMapping("/my/id")
public Result getMyId(@Auth(required=false) Long id) {
    return new Result().success(id);

# @AccessLimit 注解
## 1.简述
用于限制用户对接口的请求次数限制
## 2.如何使用？
加在需要进行限制请求频率的接口之上
## 3.示例：
@AccessLimit(seconds = 20,maxCount = 10)
@GetMapping("/hello")
    public Result getMyId() {
    return new Result().success("hello");
}
##注解参数说明： seconds代表时间（单位秒） maxCount代表次数 不写参数默认60秒一次


