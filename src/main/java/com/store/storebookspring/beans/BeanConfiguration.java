package com.store.storebookspring.beans;

import com.store.storebookspring.listeners.SessionListenerCounter;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * class to count session
 */
@Configuration
public class BeanConfiguration {

    /**
     * we declare a bean 'sessionListenerWithMetrics' to count sessions
     * @return ServletListenerRegistrationBean SessionListenerCounter
     */
    @Bean
    public ServletListenerRegistrationBean<SessionListenerCounter> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<SessionListenerCounter> listenerRegBean = new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new SessionListenerCounter());
        return listenerRegBean;
    }

    @Bean
    @SessionScope
    public CartData sessionBeanInit(){
        return new CartData();
    }

    @Bean
    @ApplicationScope
    public CartData applicationBeanInit(){
        return new CartData();
    }

}