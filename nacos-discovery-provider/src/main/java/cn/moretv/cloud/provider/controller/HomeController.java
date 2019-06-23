package cn.moretv.cloud.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CRIKEY
 */
@RestController
@Slf4j
public class HomeController {

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        log.info("请求进入：参数-->{}",name);
        return "hi,[" + name + "],provider sign to you";
    }
}
