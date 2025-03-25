package com.mapping.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;

public class MethodOverrideRequestWrapper extends HttpServletRequestWrapper {

    private final String targetMethod;

    private final String methodOverrideHeader;

    private final String methodOverrideValue;

    public MethodOverrideRequestWrapper(HttpServletRequest request, String targetMethod, String methodOverrideHeader, String methodOverrideValue) {
        super(request);
        this.targetMethod = targetMethod;
        this.methodOverrideHeader = methodOverrideHeader;
        this.methodOverrideValue = methodOverrideValue;
    }

    @Override
    public String getMethod() {
        return targetMethod;
    }

    @Override
    public String getHeader(String name) {
        if (methodOverrideHeader.equalsIgnoreCase(name)) {
            return methodOverrideValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (methodOverrideHeader.equalsIgnoreCase(name)) {
            return Collections.enumeration(Collections.singleton(methodOverrideValue));
        }
        return super.getHeaders(name);
    }

}