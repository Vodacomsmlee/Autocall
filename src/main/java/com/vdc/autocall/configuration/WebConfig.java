package com.vdc.autocall.configuration;


import com.vdc.autocall.common.interceptor.LoggerInterceptor;
import com.vdc.autocall.common.interceptor.SessionInterceptor;
import com.vdc.autocall.common.resolver.ArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //Interceptor
    @Bean
    public SessionInterceptor sessionInterceptor() { return new SessionInterceptor(); }
    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }
    @Bean
    public LoggerInterceptor loggerInterceptor() { return new LoggerInterceptor(); }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css/**", "/js/**", "/login", "/login/proc", "/logout");

        registry.addInterceptor(loggerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/css/**", "/js/**");

    }

    //parameter Resolver
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ArgumentResolver());
    }

    //index Page
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addRedirectViewController("/", "login");
    }

}
