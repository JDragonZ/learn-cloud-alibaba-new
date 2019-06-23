docs:<https://nacos.io/zh-cn/docs/architecture.html>

release:<https://github.com/alibaba/nacos/releases>

<https://github.com/spring-cloud-incubator/spring-cloud-alibaba>

## nacos功能：**Eureka+spring cloud config**

##### 服务注册与发现：适配Spring Cloud服务注册与发现的标准，默认集成了Ribbon的支持。

##### 服务限流降级：默认支持Servlet、Feign、RestTemplate、Dubbo、RocketMQ限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级Metrics监控

##### 分布式配置：支持分布式系统中的外部化配置，配置更新实时刷新

##### 消息驱动能力：基于Spring Cloud Stream 为微服务应用构建消息驱动能力。@RefreshScope

##### 分布式事务：@GlobalTransational注解，高效并且对业务零侵入的解决分布式事务问题

##### 阿里云对象存储：阿里云提供的海量、安全、低成本、高可靠的云存储对象。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。

##### 分布式任务调度：提供秒级、精准、高可靠、高可用定时（基于Cron表达式）任务调度服务。同时提供分布式任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有的Worker(schedulerx-client)上执行。

##### 阿里云短信服务：企业客户快速接入短信服务到微服务。集成阿里大于sdk。

### nacos作为注册中心

##### nacos启动

1. ​	容器中启动:docker-compose -f example/standalone.yaml up

##### nacos服务注册与发现

1. 示例代码

   ```xml
   <dependencyManagement>
           <dependencies>
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-dependencies</artifactId>
                   <version>Greenwich.SR1</version>
                   <type>pom</type>
                   <scope>import</scope>
               </dependency>
               <dependency>
                   <groupId>org.springframework.cloud</groupId>
                   <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                   <version>0.9.0.RELEASE</version>
                   <type>pom</type>
                   <scope>import</scope>
               </dependency> 
           </dependencies>
       </dependencyManagement>
   ```

   

##### nacos负载均衡、服务上下线、权重分流

1. 服务上下线：

   ![1561277384800](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561277384800.png)

2. 负载均衡

   ```java
       @Bean
       @LoadBalanced
       public RestTemplate restTemplate() {
           return new RestTemplateBuilder()
                   .setConnectTimeout(Duration.ofSeconds(5))
                   .setReadTimeout(Duration.ofSeconds(10))
                   .build();
       }
   
       @Autowired
       private LoadBalancerClient loadBalancerClient;
   ```

   ![1561278378778](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561278378778.png)

   ![1561278408873](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561278408873.png)

3. 权重分流；权重元数据 nocosServer.getMetadata(); 灰度发布；

##### nacos注册发现与整合feign与RestTemplate

1. Feign是从Netflix中分离出来的轻量级下项目，能够在类接口上添加注解，成为一个rest api客户端

2. OpenFeign Spring Cloud在netflix fiegn基础上扩展支持了Spring MVC注解。并通过 自动配置为Spring Boot应用程序提供集成。

   ```java
   @FeignClient("nacos-discovery-provider")
   public interface FeignHelloService {
   
       @GetMapping("/hello")
       String sayHello(@RequestParam String name);
   }
   ```

   

### nacos作为配置中心

##### 快速开始

1. 动态配置服务可以让开发者以中心化、外部化、动态化的方式管理所有环境的应用配置和服务配置
2. 动态配置消除了配置变更时重新部署应用和服务的需要，让配置管理变得更加高效敏捷。
3. 实现无状态服务变得简单，让服务按需弹性扩展变得容易。
4. Nacos提供了简洁的UI帮助我们管理各个环境的所有服务配置。一件回滚配置以及客户端配置更新状态跟踪在内的一系列开箱即用的配置管理特性，帮助更安全的在生产环境中管理配置和降低配置变更带来的风险。

![1561282654964](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561282654964.png)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

```yml
bootstrap.yml 配置：
spring:
  application:
    name: nacos-config-one
  cloud:
    nacos:
      config:
        group: DEFAULT_GROUP
        server-addr: 127.0.0.1:8848
        file-extension: yml
server:
  port: 9021
```



##### 配置文件加载顺序(配置隔离级别)

- spring.active:优先使用applicationname-profile 不存在时读取 applicationname

- group:同组之间是可以同名的

- namespace: 级别最高的

  ![1561285394257](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561285394257.png)

##### 共享规则

- 通用配置文件

  ```yml
   spring:
    cloud:
      nacos:
        config:
          shared-dataids: common.yml
          refreshable-dataids: common.yml
  ```

- 个性化配置文件

##### 配置持久化

- 替换嵌入式数据库为mysql,执行数据库脚本创建库与表，修改${nacos}/config/application.properties

```properties
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://192.168.25.132:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
#db.url.1=jdbc:mysql://11.163.152.9:3306/nacos_devtest?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=123456
```

![1561286361227](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561286361227.png)

![1561286398562](https://github.com/JDragonZ/learn-cloud-alibaba-new/tree/master/images/1561286398562.png)

### nacos集群搭建



![1561286537768](https://cdn.nlark.com/yuque/0/2019/jpeg/338441/1561258986171-4ddec33c-a632-4ec3-bfff-7ef4ffc33fb9.jpeg)

[官方]: https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html	"集群模式部署"

