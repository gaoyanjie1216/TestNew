package com.gyj;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.gyj")
@EnableScheduling
public class WebApplication {

	public static void main(String[] args) {

		//隐藏banner启动方式
		SpringApplication springApplication = new SpringApplication(WebApplication.class);

		//设置banner的模式为隐藏
		springApplication.setBannerMode(Banner.Mode.OFF);

		//启动springboot应用程序
		springApplication.run(args);

		//原启动方式
        //SpringApplication.run(WebApplication.class, args);
	}
}
