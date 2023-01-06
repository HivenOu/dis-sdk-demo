package com.bigdata.dis.sdk.demo.config.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfig.class);
    @Bean("hello")
    public String getHelloBean(){
        LOGGER.info("init hello bean");
        return "Hello";
    }
    @Bean("world")
    public String getWorldBean(){
        LOGGER.info("init world bean");
        return "World";
    }
}
