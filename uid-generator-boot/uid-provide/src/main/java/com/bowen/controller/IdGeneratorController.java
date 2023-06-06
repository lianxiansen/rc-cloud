//package com.bowen.controller;
//
//import com.bowen.service.UidGenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * create hqf@rc 2022-04-26
// */
//@RestController
//@RequestMapping("id-gen")
//public class IdGeneratorController {
//
//    @Autowired
//    private UidGenService uidGenService;
//
//    /**
//     * 实时生成
//     * @return
//     */
//    @GetMapping("uidGenerator")
//    public String uidGenerator() {
//        return String.valueOf(uidGenService.getUid());
//    }
//
//    /**
//     * 基于redis
//     * @return
//     */
//    @GetMapping("getUidByRedis")
//    public String getUidByRedis() {
//        return  uidGenService.getUidByRedis();
//    }
//
//
//    /**
//     * 基于本地缓存生成
//     * @return
//     */
//    @GetMapping("getUidByLocal")
//    public Long getUidByLocal() {
//        return  uidGenService.getUidByLocal();
//    }
//}
