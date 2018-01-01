package com.jzc.spring.dubbo.id;

import org.springframework.stereotype.Component;

@Component
public class IdAPI {

    public Long getId() {
        return System.currentTimeMillis();
    }

}
