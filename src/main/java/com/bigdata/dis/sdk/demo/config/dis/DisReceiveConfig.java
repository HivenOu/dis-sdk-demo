package com.bigdata.dis.sdk.demo.config.dis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DisReceiveConfig implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisReceiveConfig.class);

    @Resource
    private DisClientConfig disClientConfig;

    //spring 的上下文初始化好以后调用
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.info("start get record from huawei dis");
        new Thread(()->{
            disClientConfig.runConsumerDemo();
        }).start();
    }

}
