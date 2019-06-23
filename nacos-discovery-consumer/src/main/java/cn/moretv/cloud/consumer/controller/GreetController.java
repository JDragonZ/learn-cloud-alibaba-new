package cn.moretv.cloud.consumer.controller;

import cn.moretv.cloud.consumer.service.FeignHelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author CRIKEY
 */
@RestController
@Slf4j
public class GreetController {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private RibbonLoadBalancerClient ribbonLoadBalancerClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private FeignHelloService feignHelloService;

    @GetMapping("/greet")
    public String greeting(@RequestParam("name") String name) {
        /**
         * 1 resttemplate
         */
//        return restTemplate.getForObject("http://nacos-discovery-provider/hello?name=" + name, String.class);

        /**
         * 2 ribbonLoadBalanceClient
         */
//        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-discovery-provider");
//        log.info("请求进入：参数：{}；转发微服务地址：{}",name,serviceInstance.getUri());
//        return restTemplate.getForObject(serviceInstance.getUri() + "hello?name=" + name, String.class);
        /**
         * 3 feign
         */

       return  feignHelloService.sayHello(name);
        /**
         * 4  gateway 服务转发
         */
    }
}
