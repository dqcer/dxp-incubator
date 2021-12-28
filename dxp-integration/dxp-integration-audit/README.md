# 功能介绍

1、以用户的角度去记录操作后，前后值的变化。
2、支持国际化
3、key-value形式（码表）
4、支持业务索引搜索

# 使用说明

1、引入坐标
```java
        <dependency>
            <groupId>com.dqcer.integration</groupId>
            <artifactId>dxp-integration-audit</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

2、在需要进行稽查的接口DTO中添加`AuditDTO`成员变量。
3、在需要进行稽查的controller方法中添加注解
```java
//  新增，忽略`city`和`type`字段
@AuditLog(type = TypeEnum.INSERT, ignore = { "city",  "type"})
或者
//  更新，仅针对`ativceStr`字段
@AuditLog(type = TypeEnum.STATUS, key = {"ativceStr"})
```

