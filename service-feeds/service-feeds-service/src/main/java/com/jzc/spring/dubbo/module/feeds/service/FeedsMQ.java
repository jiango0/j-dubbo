package com.jzc.spring.dubbo.module.feeds.service;

import java.util.Map;

public interface FeedsMQ {

    boolean execute(Map<String, String> data);

    boolean callback(Map<String, String> data);

}
