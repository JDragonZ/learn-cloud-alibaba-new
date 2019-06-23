package cn.moretv.cloud.nacosconfig.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope
public class NacosConfigController {

    @Value("${my.name}")
    private String myname;

    @GetMapping("/getName")
    public String  getName(){

        return "get config from nacos  name=="+myname;

    }
}
