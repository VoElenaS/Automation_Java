package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.request.RegisterRequest;

import java.io.InputStream;
import java.util.List;

public class JsonReader {
    public static List<RegisterRequest> getRegisterRequests(String fileName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(fileName);
        return mapper.readValue(is, new TypeReference<List<RegisterRequest>>() {
        });
    }
}

