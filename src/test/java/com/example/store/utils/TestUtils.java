package com.example.store.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class TestUtils {


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper jacksonMapper = new ObjectMapper();
        jacksonMapper.registerModule(new JavaTimeModule());
        return jacksonMapper.writeValueAsBytes(object);
    }
}
