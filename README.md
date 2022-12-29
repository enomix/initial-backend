## 1. 初始化项目

### 1. 配置mybatis-plus

> 参考[官方文档](https://baomidou.com/pages/226c21/#%E5%88%9D%E5%A7%8B%E5%8C%96%E5%B7%A5%E7%A8%8B)

先创建User表

```sql
-- 用户 
-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户'
```

导入mybatis-plus的pom依赖

```xml
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.5.1</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
```

配置springboot的application.yml文件

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_initial_db?characterEncoding=utf-8&userSSL=false
    username: root
    password: 123456
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: false #驼峰和下划线转换
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      logic-delete-field: isDelete # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```

在springboot启动类中添加`@Mapper`注解, 用以扫描mapper文件夹:

```java
@SpringBootApplication
@MapperScan("com.sp.project.mapper")
public class InitialBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitialBackendApplication.class, args);
	}

}
```

编写实体类`User.java`

```java
package com.sp.project.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
```

编写 Mapper 包下的 `UserMapper`接口

```java
public interface UserMapper extends BaseMapper<User> {
}
```

在项目的测试路径下创建测试文件, 进行查询测试(数据库中要自己新建一条数据)

```java
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
```

### 2. 编写`service`层

创建`service/UserService.java`, 编写一个注册接口

```java
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}

```

编写实现类`service/impl/UserServiceImpl.java`

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final String SALT = "sp";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }
        synchronized (userAccount.intern()) {
            //账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            //加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            //插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败, 数据库错误");
            }
            return user.getId();
        }
    }
}

```

创建一个错误码枚举类`common/ErrorCode.java`, 便于返回错误信息

```java
public enum ErrorCode {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");


    private final int code;

    private final String message;

    ErrorCode(int code,  String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

```

编写业务层的测试类`UserServiceTests`测试是否能够正常调用

```java
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    void testRegister() {
        userService.userRegister("test1", "123456789", "123456789");
    }
}

```



### 3. 编写返回体

编写controller层之前, 先将返回类给写出来, 便于返回状态码和与之对应的状态信息还有内容体. 创建`common/BaseResponse.java`

```java
/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}

```

再创建一个返回工具类, 封装一下返回成功或者失败时的写法`common/ResultUtils.java`

```java
/**
 * 返回工具类
 *
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}

```



### 4. 异常处理

编写自定义异常类`exception/BusinessException.java`

```java
/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException{

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}

```

编写全局异常处理器`GlobalExceptionHandler.java`, 用于捕获全局抛出的异常, 返回对应信息给前端

```java
/**
 * 全局异常处理器
 * 用于捕获全局抛出的异常, 返回对应信息给前端
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}

```

### 5. 编写`controller`层

先创建dto, 创建`model/dto/user/UserRegisterRequest.java`

```java
/**
 * 用户注册登录dto
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3890560336727616810L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}

```

> **为什么要用到DTO? **
>
> Data Transfer Object数据传输对象, 主要用于远程调用需要大量传输对象的时候, 比如说User有10个属性, 我们只用到其中3个, 就没有必要把整个PO(persistant object持久对象) 给传递到前端. 这样操作不会暴露服务端表结构

创建`controller/Controller.java`. 编写用户的注册接口

```java
/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
}

```



### 6. 使用knife4j API文档

> [参考文档](https://segmentfault.com/a/1190000041143413)

首先导入pom依赖

```xml
        <!--knife4j接口文档-->
		<dependency>
			<groupId>com.github.xiaoymin</groupId>
			<artifactId>knife4j-spring-boot-starter</artifactId>
			<version>3.0.3</version>
		</dependency>
```

然后新建一个配置文件`config/Knife4jConfiguration.java`

```java
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName="3.X版本";
        Docket docket=new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("Initial Backend")
                        .description("# backend")
                        .version("1.0")
                        .build())
                //分组名称
                .groupName(groupName)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.sp.project.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}

```

如果启动项目的时候出现了异常: **Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException**,是因为项目的springboot版本太高, 需要修改下配置`application.yml`

```yml
spring:
  mvc:
    pathmatch:
      # Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher
      # 所以需要配置此参数
      matching-strategy: ant_path_matcher
```

启动项目, 访问api文档地址http://localhost:8080/doc.html (ip+端口+/doc.html)

## 2. 项目优化
1. 优化登录流程, 避免重复登录(使用jwt)

