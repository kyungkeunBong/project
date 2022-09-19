package com.kakaobank.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KakaobankBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaobankBlogApplication.class, args);
	}

}
