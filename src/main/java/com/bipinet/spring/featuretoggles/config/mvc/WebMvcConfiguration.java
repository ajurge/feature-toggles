package com.bipinet.spring.featuretoggles.config.mvc;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.bipinet.spring.featuretoggles.util.Util.getPageRelativePath;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    public static final String LOGIN_PAGE_NAME = "login";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(getPageRelativePath(LOGIN_PAGE_NAME)).setViewName(LOGIN_PAGE_NAME);
        registry.addViewController("/loginSuccess").setViewName("index");
        registry.addViewController("/form-login").setViewName("form-login/index");
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}