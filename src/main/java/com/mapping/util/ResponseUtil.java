package com.mapping.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static void handleError(HttpServletResponse response, HttpStatus status, String errorTitle, Exception e) throws IOException {
        handleError(response, status, errorTitle, e.getMessage());
    }

    public static void handleError(HttpServletResponse response, HttpStatus status, String errorTitle, String errorMessage) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorTitle);
        errorResponse.put("message", errorMessage);

        response.getWriter().write(errorResponse.toString());
    }

    public static void handleError(HttpServletResponse response, Exception e) throws IOException {
        handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error processing request", e.getMessage());
    }

}
