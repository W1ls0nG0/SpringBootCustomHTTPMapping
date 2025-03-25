package com.mapping.track;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "http.filter.track")
@EnableConfigurationProperties
public class TrackHttpMethodFilterProperties {

    private     boolean         enabled                 =   true;
    private     String          targetMethod            =   "GET";
    private     Set<String>     allowedPaths            =   Collections.emptySet();
    private     String          methodOverrideHeader    =   "X-HTTP-Method-Override";
    private     boolean         auditEnabled            =   false;
    private     String          auditLogFormat          =   "%s - TRACK request processed for path: %s";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public Set<String> getAllowedPaths() {
        return allowedPaths;
    }

    public void setAllowedPaths(Set<String> allowedPaths) {
        this.allowedPaths = allowedPaths;
    }

    public String getMethodOverrideHeader() {
        return methodOverrideHeader;
    }

    public void setMethodOverrideHeader(String methodOverrideHeader) {
        this.methodOverrideHeader = methodOverrideHeader;
    }

    public boolean isAuditEnabled() {
        return auditEnabled;
    }

    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }

    public String getAuditLogFormat() {
        return auditLogFormat;
    }

    public void setAuditLogFormat(String auditLogFormat) {
        this.auditLogFormat = auditLogFormat;
    }

}
