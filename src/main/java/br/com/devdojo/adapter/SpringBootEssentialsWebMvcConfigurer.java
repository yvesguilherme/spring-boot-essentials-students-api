package br.com.devdojo.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author yvesguilherme on 29/06/2020.
 * @project spring-boot-essentials
 */

@Configuration
public class SpringBootEssentialsWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver phmArgumentResolver = new PageableHandlerMethodArgumentResolver();
        PageRequest pageRequest = PageRequest.of(0, 5);

        phmArgumentResolver.setFallbackPageable(pageRequest);
        resolvers.add(phmArgumentResolver);
    }
}
