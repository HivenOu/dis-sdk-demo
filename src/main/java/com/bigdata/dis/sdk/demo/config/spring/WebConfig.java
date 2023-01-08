package com.bigdata.dis.sdk.demo.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.bigdata.dis.sdk.demo.controller")
@EnableWebMvc
public class WebConfig  implements WebMvcConfigurer {
    //default-servlet-handler
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //参考文档：https://www.jianshu.com/p/7e026b76424e
    //配置  * 1. 注解扫描      2. 视图解析器        3. view-controller  4. default-servlet-handler
    // * 5. mvc注解驱动   6. 文件上传解析器     7. 异常处理         8.拦截器


    // 6. 文件上传解析器
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //10MB大小
        commonsMultipartResolver.setMaxUploadSize(10485760);
        return commonsMultipartResolver;
    }
}
