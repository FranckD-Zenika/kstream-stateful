package com.zenika.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Optional;

public class SerdesUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SuppressWarnings("java:S1168") // null returned instead of empty array
    public static byte[] writeValueAsByte(Object o) {
        try {
            return MAPPER.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T readValue(byte[] bytes, Class<T> clazz) {
        try {
            return MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> Serializer<T> serializer() {
        return (s, o) -> Optional.ofNullable(o)
                .map(SerdesUtil::writeValueAsByte)
                .orElse(null);
    }

    public static <T> Deserializer<T> deserializer(Class<T> clazz) {
        return (s, b) -> Optional.ofNullable(b)
                .map(bytes -> SerdesUtil.readValue(b, clazz))
                .orElse(null);
    }

    private SerdesUtil() {}

}
