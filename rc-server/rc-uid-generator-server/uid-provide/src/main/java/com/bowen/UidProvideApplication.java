package com.bowen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.baidu.fsg.uid")
@EnableAsync
public class UidProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(UidProvideApplication.class, args);
	}

}
