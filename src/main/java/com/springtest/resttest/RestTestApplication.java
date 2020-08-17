package com.springtest.resttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTestApplication.class, args);
    }

    // @SpringBootApplication 어노테이션이 있는 곳에 @Bean 을 등록 시
    // 스프링부트가 초기화될 때 해당 정보가 메모리에 저장된다

    /**
     * 다국어 설정
     * 기본값은 KOREA로 설정한다
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        // Session 을 이용하여 Locale 값을 불러들인다
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();

        // 기본 언어 설정: KOREA
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

}
