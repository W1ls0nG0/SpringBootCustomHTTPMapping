package com.mapping.sync;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "http.filter.sync")
@EnableConfigurationProperties
public class SyncHttpMethodFilterProperties {

    private     boolean         enabled                 =   true;
    private     String          targetMethod            =   "PATCH";
    private     Set<String>     allowedPaths            =   Collections.emptySet();
    private     String          methodOverrideHeader    =   "X-HTTP-Method-Override";

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

}