package com.abee.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xincong yao
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class, Object> table = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    public static <T> T of(Class<T> clazz) {
        T instance = (T) table.get(clazz);
        if (instance != null) {
            return instance;
        }

        table.put(clazz, bean(clazz));
        return (T) table.get(clazz);
    }

    private static <T> T bean(String name) {
        return (T) applicationContext.getBean(name);
    }

    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
