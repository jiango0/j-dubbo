package com.jzc.spring.dubbo.module.release;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;

public interface HelloEsApi {

    PageList<HelloInfo> hello(Integer pageNo, Integer pageSize);

}
