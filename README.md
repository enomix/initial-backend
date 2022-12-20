### 1. 初始化项目

1. 配置mybatis-plus

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

   