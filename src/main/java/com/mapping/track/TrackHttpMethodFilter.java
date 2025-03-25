package com.mapping.track;

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
public class TrackHttpMethodFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TrackHttpMethodFilter.class);

    private static final String TRACK_METHOD = "TRACK";

    private final TrackHttpMethodFilterProperties properties;

    public TrackHttpMethodFilter(TrackHttpMethodFilterProperties properties) {
        this.properties = properties;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        if (!properties.isEnabled()) {
            return true;
        }

        boolean isTrackMethod = TRACK_METHOD.equalsIgnoreCase(request.getMethod());
        if (!isTrackMethod) {
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
            if (TRACK_METHOD.equalsIgnoreCase(request.getMethod())) {
                if (properties.isAuditEnabled()) {
                    log.info("Audit Log: Address={}, URI={}", request.getRemoteAddr(), request.getRequestURI());
                }
                log.debug("Converting TRACK request to {} for path: {}", properties.getTargetMethod(), request.getRequestURI());
                HttpServletRequest wrappedRequest = new MethodOverrideRequestWrapper(request, properties.getTargetMethod(), properties.getMethodOverrideHeader(), TRACK_METHOD);
                filterChain.doFilter(wrappedRequest, response);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error processing TRACK request", e);
            ResponseUtil.handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error processing TRACK request", e.getMessage());
        }
    }

}
