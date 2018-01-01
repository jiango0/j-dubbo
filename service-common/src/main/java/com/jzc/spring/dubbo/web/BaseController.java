package com.jzc.spring.dubbo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description: 控制器基类
 * @Date: Created in 2017 2017/11/16 15:24
 * @Author: pn
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Value("${tenantId}")
    protected String tenantId;

}
