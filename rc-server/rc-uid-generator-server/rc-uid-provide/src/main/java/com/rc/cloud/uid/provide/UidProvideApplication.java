package com.rc.cloud.uid.provide;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.rc.cloud")
@EnableAsync
public class UidProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(UidProvideApplication.class, args);
	}

}
