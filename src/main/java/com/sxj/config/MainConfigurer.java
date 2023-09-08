package com.sxj.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MainConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                /*注册主页*/
                registry.addViewController("/").setViewName("redirect:mainMenuController");
                registry.addViewController("/index").setViewName("redirect:mainMenuController");
                registry.addViewController("/main").setViewName("redirect:mainMenuController");
            }
        };
    }
}