package com.jzc.spring.dubbo.module.hello.web;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.HelloEsApi;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("hello")
public class HelloController extends BaseController {

    @Autowired
    HelloEsApi helloEsApi;

    @ResponseBody
    @RequestMapping(value = "")
    public PageList<HelloInfo> hello(Integer pageNo, Integer pageSize) {
        return helloEsApi.hello(pageNo, pageSize);
    }


}
