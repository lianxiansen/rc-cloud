/**
 * @author oliveoil
 * date 2023-06-02 07:59
 */
package com.rc.cloud.app.system.biz.controller.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        int i = 3/0;
        return "hello2";
    }
}
