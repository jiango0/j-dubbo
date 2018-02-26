package com.jzc.spring.dubbo.module.release.web;

import com.jzc.spring.dubbo.entity.PageList;
import com.jzc.spring.dubbo.module.release.ReleaseApi;
import com.jzc.spring.dubbo.module.release.dto.SearchDto;
import com.jzc.spring.dubbo.module.release.entity.HelloInfo;
import com.jzc.spring.dubbo.web.BaseController;
import com.jzc.spring.dubbo.web.DubboResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("release")
public class ReleaseController extends BaseController {

    @Autowired
    ReleaseApi releaseApi;

    @ResponseBody
    @RequestMapping(value = "list")
    public DubboResponse<PageList<HelloInfo>> list(SearchDto searchDto) {
        return releaseApi.list(searchDto);
    }

}
