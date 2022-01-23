package com.jd.omni.membership.domain.util.spring;

import com.jd.omni.membership.event.common.DomainEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zaomeng
 * @date 2021/5/19 2:19 下午
 * @description spring 上下文
 */
@Component
public class SpringContextHelper implements ApplicationContextAware, ApplicationEventPublisherAware {
    /**
     * spring上下文
     */
    private static ApplicationContext context;

    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHelper.context =applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanByName(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static <T> T getBeanByClass(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static void publishEvent(DomainEvent event){
        if (event == null) {
            return;
        }
        publisher.publishEvent(event);
    }

    public static void publishEvent(List<DomainEvent> events){
        if(events == null || events.isEmpty()){
            return;
        }
        for(DomainEvent event : events){
            publishEvent(event);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        SpringContextHelper.publisher = applicationEventPublisher;
    }
}
