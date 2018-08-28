package com.otawang.bootspringboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BootSpringBootApplication {

    public static void main(String[] args) {
        //가장 기본적인 방법
        SpringApplication.run(BootSpringBootApplication.class, args);

        //새로운 로컬 인스턴스를 만들어서 생성하는 방법
//        SpringApplication app = new SpringApplication(BootSpringBootApplication.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        //유연한 빌더를 이용해서 생성하는 방법
//        new SpringApplicationBuilder()
//                .sources(Parent.class)
//                .child(BootSpringBootApplication.class)
//                .bannerMode(Banner.Mode.OFF)
//                .run(args);

    }
}
