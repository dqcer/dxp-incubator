# 背景
> 很多时候我们会制定一些项目的规范，但是规范的落地实施与坚持只能依靠review来检查，Archunit以单元测试的形式通过扫描类路径(甚至Jar)包下的所有类，通过单元测试的形式对各个规范进行代码编写，
> 如果项目代码中有违背对应的单测规范，那么单元测试将会不通过，这样就可以从CI/CD层面彻底把控项项目架构和编码规范。

# 简介

Archunit是一个免费、简单、可扩展的类库，用于检查Java代码的体系结构。提供检查包和类的依赖关系、调用层次和切面的依赖关系、循环依
赖检查等其他功能。它通过导入所有类的代码结构，基于Java字节码分析实现这一点。的主要关注点是使用任何普通的Java单元测试框架自动测试
代码体系结构和编码规则。

# 官方入口

- 开源地址[https://github.com/TNG/ArchUnit.git](https://github.com/TNG/ArchUnit.git)
- 官网地址[https://github.com/TNG/ArchUnit.git](https://github.com/TNG/ArchUnit.git)

# 快速开始

## 导入坐标

```xml
        <dependency>
            <groupId>com.tngtech.archunit</groupId>
            <artifactId>archunit-junit5</artifactId>
            <version>0.23.1</version>
        </dependency>
```

