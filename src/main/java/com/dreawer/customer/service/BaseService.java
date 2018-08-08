package com.dreawer.customer.service;

import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * <CODE>BaseService</CODE>
 *
 * @author fenrir
 * @Date 18-4-3
 */
@ComponentScan
public class BaseService {

    protected Map<String, Object> convertMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        return map;
    }
}
