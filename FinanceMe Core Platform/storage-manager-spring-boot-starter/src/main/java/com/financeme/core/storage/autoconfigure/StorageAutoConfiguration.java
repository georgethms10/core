package com.financeme.core.storage.autoconfigure;

import com.financeme.core.storage.StorageClient;
import com.financeme.core.storage.filesystem.LocalFileSystemStorageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@ConditionalOnProperty(
        prefix = "financeme.storage",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class StorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public StorageClient storageClient(StorageProperties properties) {
        Path rootPath = Paths.get(properties.getBasePath());
        return new LocalFileSystemStorageClient(rootPath);
    }
}