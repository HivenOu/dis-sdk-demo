package com.bigdata.dis.sdk.demo.config.dis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisTest.class);
    @Bean("stringBean")
    public String getStringBean(){
        LOGGER.info("#################init string bean################");
        return "string";
    }
}
