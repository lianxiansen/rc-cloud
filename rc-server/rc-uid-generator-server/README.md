# 百度uid-generator唯一ID生成器

UidGenerator是Java实现的, 基于Snowflake算法的唯一ID生成器。UidGenerator以组件形式工作在应用项目中, 支持自定义workerId位数和初始化策略, 从而适用于docker等虚拟化环境下实例自动重启、漂移等场景。 在实现上, UidGenerator通过借用未来时间来解决sequence天然存在的并发限制; 采用RingBuffer来缓存已生成的UID, 并行化UID的生产和消费, 同时对CacheLine补齐，避免了由RingBuffer带来的硬件级「伪共享」问题. 最终单机QPS可达600万。依赖版本：Java8及以上版本, MySQL(内置WorkerID分配器, 启动阶段通过DB进行分配; 如自定义实现, 则DB非必选依赖）

### 中文文档

https://github.com/baidu/uid-generator/blob/master/README.zh_cn.md

### 官方源码

https://github.com/baidu/uid-generator

### 案例使用

全局ID生成器是服务化系统的基础设施，其在保障系统的正确运行和高可用方面发挥着重要作用。而关于全局ID生成算法首屈一指的当属 Snowflake雪花算法，然而 Snowflake本身很难在现实项目中直接使用，因此实际应用时需要一种可落地的方案。

UidGenerator 由百度开发，是Java实现的, 基于 Snowflake算法的唯一ID生成器。UidGenerator以组件形式工作在应用项目中, 支持自定义workerId位数和初始化策略, 从而适用于 docker等虚拟化环境下实例自动重启、漂移等场景。

基于百度开源分布式id生成器二开,项目中来集成 UidGenerator这一工程来作为项目的全局唯一 ID生成器。 
本项目多种生成方式,支持 reids ,异步调用,启动时自动加载,实时生成,xxljob任务调度等离线预生成id 可分布式部署

>本文就在项目中来集成 UidGenerator这一工程来作为项目的全局唯一 ID生成器。
>基于百度开源分布式id生成器二开,项目中来集成 UidGenerator这一工程来作为项目的全局唯一 ID生成器。 本项目多种生成方式,支持 reids ,异步调用,启动时自动加载,实时生成,xxljob任务调度等离线预生成id 可分布式部署

案例源码地址：https://gitee.com/ComeOnBaye/uid-generator.git

![](https://gitee.com/zhangbw666/img-folder/raw/master/img-other/20210313001.jpg)

本案例主要是讲解springboot集成百度uid-generator唯一ID生成器，比如订单中心生成唯一订单号，可调用此服务生成全局唯一ID供业务使用。可作为单独的服务独立部署，做成一个微服务供其他服务调用。

- 说明
```text
升级spring boot 版本： 2.1.10.RELEASE

升级 mybatis，mybatis-spring 版本

升级 mysql-connector-java 版本：8.0.12

升级 junit 版本
```

- 启动入口
```java
uid-provide 中的 UidProvideApplication
```

- 浏览器访问
```html
http://localhost:8080/uidGenerator
```

- 返回全局UID
```text
1071649247213936641
```

好了，以上步骤就是实现了全局ID的生成，通过调用http://localhost:8080/uidGenerator接口，即可很方便的生成全局ID,是不是很简单。接下来，我们需要讲解详细过程，先讲讲官方源码是怎样玩的。

### 官方源码实战

- 创建数据库表

导入官网数据库SQL [https://github.com/baidu/uid-generator/blob/master/src/main/scripts/WORKER_NODE.sql](https://github.com/baidu/uid-generator/blob/master/src/main/scripts/WORKER_NODE.sql)
也就是一张表  
我这里是在`uid_generator`库中，创建了这张表  
```sql
DROP TABLE IF EXISTS WORKER_NODE;
CREATE TABLE WORKER_NODE
(
ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
HOST_NAME VARCHAR(64) NOT NULL COMMENT 'host name',
PORT VARCHAR(64) NOT NULL COMMENT 'port',
TYPE INT NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
LAUNCH_DATE DATE NOT NULL COMMENT 'launch date',
MODIFIED datetime NOT NULL COMMENT 'modified time',
CREATED datetime NOT NULL COMMENT 'created time',
PRIMARY KEY(ID)
)
COMMENT='DB WorkerID Assigner for UID Generator',ENGINE = INNODB;
```
- mysql配置信息更改  

因为升级到8.x ,配置文件部分也要跟着修改`uid-generator` 下，测试文件夹下的资源包`uid/mysql.properties` 以下修改为  

```java
mysql.driver=com.mysql.cj.jdbc.Driver
```

修改完成后，配置好数据库相关参数，这样单元测试即可执行成功。接下来，我们就讲讲在SpringBoot中如何基于百度uid-generator唯一ID生成器实现全局ID的生成。

### 案例详解

计划将全局生成唯一ID作为一个服务提供者，供其他微服务使用调用。这里创建了一个项目，项目中包含两个子项目一个是`uid-generator`官方本身，当然你也可以不需要放到本项目中，直接使用官方的自行打包即可，一个是`uid-provide` 服务提供者。以下说明的主要是`服务提供者`

- 创建 子项目 uid-provide

POM配置文件如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.bowen</groupId>
    <artifactId>uid-provide</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>uid-provide</name>

    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>com.bowen</groupId>
        <artifactId>uid-generator-boot</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!--for mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>

        <!--for Mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version>8.0.12</version>
        </dependency>

        <!-- druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.16</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- uid-generator jar 包 -->
        <dependency>
            <groupId>com.bowen</groupId>
            <artifactId>uid-generator</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

- 复制 mapper 

先在`uid-provide`项目资源包路径下创建`mapper`文件夹，然后到官方`uid-generator`资源包路径下`META-INF/mybatis/mapper/WORKER_NODE.xml` 复制`WORKER_NODE.xml`文件，粘贴到该文件夹`mapper`内。

- cache id 配置文件

>UidGenerator 有两个具体的实现类，分别是 DefaultUidGenerator 和 CachedUidGenerator，不过官方也推荐了对于性能比较敏感的项目应使用后者，因此本文也使用 CachedUidGenerator，而对于 DefaultUidGenerator不做过多阐述。

先在`uid-provide`项目资源包路径下创建`uid`文件夹，然后到官方`uid-generator` `测试` [注意:`这里是测试资源包`] 资源包路径下`uid/cached-uid-spring.xml` 复制`cached-uid-spring.xml`文件，粘贴到该文件夹`uid`内。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd">

    <!-- UID generator -->
    <bean id="disposableWorkerIdAssigner" class="DisposableWorkerIdAssigner" />

    <bean id="cachedUidGenerator" class="CachedUidGenerator">
        <property name="workerIdAssigner" ref="disposableWorkerIdAssigner" />
        <property name="epochStr" value="2020-03-17"/>
        <!-- 以下为可选配置, 如未指定将采用默认值 -->
        <!-- RingBuffer size扩容参数, 可提高UID生成的吞吐量. -->
        <!-- 默认:3， 原bufferSize=8192, 扩容后bufferSize= 8192 << 3 = 65536 -->
        <!--<property name="boostPower" value="3"></property>-->

        <!-- 指定何时向RingBuffer中填充UID, 取值为百分比(0, 100), 默认为50 -->
        <!-- 举例: bufferSize=1024, paddingFactor=50 -> threshold=1024 * 50 / 100 = 512. -->
        <!-- 当环上可用UID数量 < 512时, 将自动对RingBuffer进行填充补全 -->
        <!--<property name="paddingFactor" value="50"></property>-->

        <!-- 另外一种RingBuffer填充时机, 在Schedule线程中, 周期性检查填充 -->
        <!-- 默认:不配置此项, 即不实用Schedule线程. 如需使用, 请指定Schedule线程时间间隔, 单位:秒 -->
        <!--<property name="scheduleInterval" value="60"></property>-->

        <!-- 拒绝策略: 当环已满, 无法继续填充时 -->
        <!-- 默认无需指定, 将丢弃Put操作, 仅日志记录. 如有特殊需求, 请实现RejectedPutBufferHandler接口(支持Lambda表达式) -->
        <!--<property name="rejectedPutBufferHandler" ref="XxxxYourPutRejectPolicy"></property>-->

        <!-- 拒绝策略: 当环已空, 无法继续获取时 -->
        <!-- 默认无需指定, 将记录日志, 并抛出UidGenerateException异常. 如有特殊需求, 请实现RejectedTakeBufferHandler接口(支持Lambda表达式) -->
        <!--<property name="rejectedPutBufferHandler" ref="XxxxYourPutRejectPolicy"></property>-->

    </bean>

</beans>
```

最后根据需要配置参数，可以看官方说明。

- 创建 spring boot 启动入口

主要就是加上注解`@MapperScan("com.baidu.fsg.uid")`让`mybatis`能扫描到`Mapper`类的包的路径。

```java
package com.bowen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baidu.fsg.uid")
public class UidProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(UidProvideApplication.class, args);
	}

}
```

- 创建配置

```java
package com.bowen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:uid/cached-uid-spring.xml" })
public class UidConfig {
}
```
- 创建服务接口

```java
package com.bowen.service;

import UidGenerator;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UidGenService {

    @Resource(name = "cachedUidGenerator")
    private UidGenerator uidGenerator;

    public long getUid() {
        return uidGenerator.getUID();
    }
}
```
主要说明一下`@Resource(name = "cachedUidGenerator")` 以往错误都是少了这里，`没有`标明`注入来源`

- 控制器

```java
@RestController
public class UidController {

    @Autowired
    private UidGenService uidGenService;

    @GetMapping("/uidGenerator")
    public String UidGenerator() {
        return String.valueOf(uidGenService.getUid());
    }
}
```
- 项目配置文件
```xml
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/uid_generator?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
```

- 具体演示

我们每启动一次 Spring Boot工程，其即会自动去 MySQL数据的 WORKER_NODE表中插入一行关于工作节点的记录，类似下图所示：

![](https://gitee.com/zhangbw666/img-folder/raw/master/img-other/20210313002.jpg)

接下来我们浏览器访问：http://localhost:8080/uidGenerator

![](https://gitee.com/zhangbw666/img-folder/raw/master/img-other/20210313003.jpg)

OK，全局唯一全局IDID已经成功生成并返回！由于能力有限，若有错误或者不当之处，还请大家批评指正，一起学习交流！

到此，我们就实现了在SpringBoot中基于百度uid-generator唯一ID生成器实现全局ID的生成，我们将此项目部署到服务器中，独立成一个微服务，即可对外提供生成全局ID服务。
