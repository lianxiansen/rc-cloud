/**
 * @author oliveoil
 * date 2023-06-16 09:12
 */
package com.rc.cloud.ops.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }


}
