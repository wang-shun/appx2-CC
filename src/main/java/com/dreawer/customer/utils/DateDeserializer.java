package com.dreawer.customer.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Date;

/**
 * Created with antnest-platform
 * User: chenyuan
 * Date: 12/22/14
 * Time: 4:39 PM
 */
public class DateDeserializer implements JsonDeserializer<java.sql.Date> {

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new java.sql.Date(json.getAsJsonPrimitive().getAsLong());
    }
}