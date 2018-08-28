package com.otawang.bootspringboot.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MyBean {
    @Value("${name}")
    private String name;

    @Value("${my.secret}")
    private String random;

}
