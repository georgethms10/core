package com.financeme.core.storage.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "financeme.storage")
public class StorageProperties {

    /**
     * Base directory where files will be stored
     */
    private String basePath = "./data";

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}