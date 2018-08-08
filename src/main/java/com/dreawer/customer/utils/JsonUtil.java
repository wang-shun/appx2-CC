package com.dreawer.customer.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtil {


	private static ObjectMapper mapper = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger("JsonUtil");

	public static ObjectMapper getObjectMapper() {
		// 允许没有双引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 设置忽略没有的字段
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * 将JsonString转为对象
	 *
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将JsonString转为对象
	 *
	 * @param content
	 * @return
	 */
	public static <T> T readValue(String content, TypeReference<?> valueTypeRef) {
		try {
			return getObjectMapper().readValue(content, valueTypeRef);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将对象转为JsonString
	 *
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}
}
