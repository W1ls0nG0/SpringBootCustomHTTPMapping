package com.mapping.sync;

import com.mapping.util.MethodOverrideRequestWrapper;
import com.mapping.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SyncHttpMethodFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SyncHttpMethodFilter.class);

    private static final String SYNC_METHOD = "SYNC";

    private final SyncHttpMethodFilterProperties properties;

    public SyncHttpMethodFilter(SyncHttpMethodFilterProperties properties) {
        this.properties = properties;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        if (!properties.isEnabled()) {
            return true;
        }
        boolean isSyncMethod = SYNC_METHOD.equalsIgnoreCase(request.getMethod());
        if (!isSyncMethod) {
            return true;
        }
        if (properties.getAllowedPaths().isEmpty()) {
            return false;
        }
        String path = request.getServletPath();
        return properties.getAllowedPaths().stream().noneMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {
        try {
            if (SYNC_METHOD.equalsIgnoreCase(request.getMethod())) {
                log.debug("Converting SYNC request to {} for path: {}", properties.getTargetMethod(), request.getRequestURI());
                HttpServletRequest wrappedRequest = new MethodOverrideRequestWrapper(request, properties.getTargetMethod(), properties.getMethodOverrideHeader(), SYNC_METHOD);
                filterChain.doFilter(wrappedRequest, response);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error processing SYNC request", e);
            ResponseUtil.handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error processing SYNC request", e.getMessage());
        }
    }

}