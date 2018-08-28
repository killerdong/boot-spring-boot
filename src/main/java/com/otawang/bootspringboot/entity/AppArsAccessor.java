package com.otawang.bootspringboot.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AppArsAccessor {

    @Autowired
    public AppArsAccessor(ApplicationArguments arguments) {

        boolean debug = arguments.containsOption("debug");
        log.debug("debug={}", debug);
        List<String> files = arguments.getNonOptionArgs();
        log.debug("files={}", files);

    }
}
