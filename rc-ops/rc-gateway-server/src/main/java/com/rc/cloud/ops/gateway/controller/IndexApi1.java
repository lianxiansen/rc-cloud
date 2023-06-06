package com.rc.cloud.ops.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

public interface IndexApi1 {
    @GetMapping("/index")
    Mono<String> index();
}
