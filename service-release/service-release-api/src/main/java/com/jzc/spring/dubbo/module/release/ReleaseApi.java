package com.jzc.spring.dubbo.module.release;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.dto.SearchDto;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.web.DubboResponse;

public interface ReleaseApi {

    DubboResponse<PageList<HelloInfo>> list(SearchDto searchDto);

}
