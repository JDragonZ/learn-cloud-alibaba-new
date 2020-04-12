package cn.moretv.cloud.nacosdubbo.controller;

import cn.moretv.cloud.nacosdubbo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/test")
public class TestController {

    @Reference
    HelloService helloService;

    @GetMapping("/test")
    public String test() {
        return helloService.sayHello("zhangjilong nacos dubbo");
    }
}
