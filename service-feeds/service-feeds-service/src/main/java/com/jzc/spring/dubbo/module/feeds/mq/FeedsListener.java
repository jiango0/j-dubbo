package com.jzc.spring.dubbo.module.feeds.mq;

import com.jzc.spring.dubbo.context.SpringContextHolder;
import com.jzc.spring.dubbo.module.feeds.service.FeedsMQ;
import com.jzc.spring.dubbo.mq.AbsMQlistener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FeedsListener extends AbsMQlistener {

    private static Logger logger = LoggerFactory.getLogger(FeedsListener.class);

    @Override
    public void handleAsynMessage(Map<String, String> data) {
        FeedsMQ service = null;
        try {
            String path = data.get("path");
            service = (FeedsMQ) SpringContextHolder.getBean(Class.forName(path));
            if(!service.execute(data)){
                this.callback(data, service);
            }
        } catch (Exception e) {
            logger.error("【FeedsListener】异常", e);
            this.callback(data, service);
        }
    }

    private void callback(Map<String, String> data, FeedsMQ service) {
        if(service != null){
            try {
                if(!service.callback(data)){
                    logger.error("【FeedsListener】callback异常 : return false");
                }
            } catch (Exception e) {
                logger.error("【FeedsListener】callback异常", e);
            }
        }
    }

}
