package com.jzc.spring.dubbo.module.release.service;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;

public interface HelloEsService {

    PageList<HelloInfo> hello(Integer pageNo, Integer pageSize);

}
