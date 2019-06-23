package cn.moretv.cloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nacos-discovery-provider")
public interface FeignHelloService {

    @GetMapping("/hello")
    String sayHello(@RequestParam String name);
}
