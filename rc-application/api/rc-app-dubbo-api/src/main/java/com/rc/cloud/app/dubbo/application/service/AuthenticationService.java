package com.rc.cloud.app.dubbo.application.service;



public interface AuthenticationService {

    String validateCaptcha(String uuid, String captchaCode);


}
