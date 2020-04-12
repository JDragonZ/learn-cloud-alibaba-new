package cn.moretv.cloud.nacosdubbo.service.impl;

import cn.moretv.cloud.nacosdubbo.service.HelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
