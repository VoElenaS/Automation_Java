package org.example.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ValidationResponse {

    List<ErrorElements> detail;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class ErrorElements {
        String type;
        List<String> loc;
        String msg;
        String input;
        Map<String, Object> ctx;
    }
}

