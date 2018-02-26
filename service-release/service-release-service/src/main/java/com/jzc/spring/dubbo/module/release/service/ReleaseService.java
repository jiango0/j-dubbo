package com.jzc.spring.dubbo.module.release.service;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.dto.SearchDto;

public interface ReleaseService {

    /**
     * 查询
     * */
    <T> PageList<T> list(Class<T> t, SearchDto searchDto);

}
