package com.jzc.spring.dubbo.module.release.provider;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.HelloEsApi;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.module.release.service.HelloEsService;
import com.jzc.spring.dubbo.module.release.service.impl.HelloEsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloEsProvider implements HelloEsApi {

    private static final Logger logger = LoggerFactory.getLogger(HelloEsServiceImpl.class);

    @Autowired
    private HelloEsService helloEsService;

    public PageList<HelloInfo> hello(Integer pageNo, Integer pageSize) {
        return helloEsService.hello(pageNo, pageSize);
    }

}
