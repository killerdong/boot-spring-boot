package com.otawang.bootspringboot.api;

import com.otawang.bootspringboot.entity.MyBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private MyBean myBean;

    public HelloController(MyBean myBean) {
        this.myBean = myBean;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return this.myBean.getName() + " " + this.myBean.getRandom();

    }
}
