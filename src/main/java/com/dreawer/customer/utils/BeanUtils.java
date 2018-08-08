package com.dreawer.customer.utils;

import java.util.Map;

/**
 * <CODE>BeanUtils</CODE>
 * 对象转换工具类
 *
 * @author fenrir
 * @Date 18-4-8
 */
public class BeanUtils {

    public static <T> T mapToObject(Map<String, Object> map, Class<T> classOfT) throws Exception {
        if (map == null)
            return null;

        T target = classOfT.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(target, map);

        return target;
    }

    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null)
            return null;

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}

