package com.jzc.spring.dubbo.module.feeds.mq;

import com.jzc.spring.dubbo.mq.MQSender;
import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FeedsSenderManager extends MQSender {

    @Autowired
    private RabbitTemplate directTemplate;

    /** 基础直连交换机 */
    private static final int APP_DIRECT_COMMON = 1;
    /** 扇形交换机 */
    private static final int APP_FANOUT_ = 2;

    private static final String QUEUE_FEEDS = "feeds.queue";

    @Override
    public RabbitTemplate checkTemplate(int app) {
        switch (app) {
            case APP_DIRECT_COMMON:
                return directTemplate;
            case APP_FANOUT_:
                return null;
            default:
                return null;
        }
    }

    public void sendEventReport(Map<String, String> params) {
        if (MapUtils.isNotEmpty(params)) {
            this.send(APP_DIRECT_COMMON, QUEUE_FEEDS, params);
        }
    }

}
