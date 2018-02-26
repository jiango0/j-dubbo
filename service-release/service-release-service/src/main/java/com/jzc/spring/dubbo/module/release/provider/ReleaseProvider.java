package com.jzc.spring.dubbo.module.release.provider;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.ReleaseApi;
import com.jzc.spring.dubbo.module.release.dto.SearchDto;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.module.release.service.ReleaseService;
import com.jzc.spring.dubbo.web.DubboResponse;
import com.jzc.spring.dubbo.web.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseProvider implements ReleaseApi {

    @Autowired
    ReleaseService releaseService;

    public DubboResponse<PageList<HelloInfo>> list(SearchDto searchDto) {
        return ResponseModel.returnObjectSuccess(releaseService.list(HelloInfo.class, searchDto));
    }

}
