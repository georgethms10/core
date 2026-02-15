package com.financeme.core.storage.filesystem;

import com.financeme.core.storage.StorageClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LocalFileSystemStorageClient implements StorageClient {

    private final Path rootDir;

    public LocalFileSystemStorageClient(Path rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public String upload(String path, InputStream inputStream) {
        try {
            String sanitized = sanitizeFileName(path);
            Path target = rootDir.resolve(sanitized);

            Files.createDirectories(target.getParent());
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);

            return sanitized;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public InputStream download(String path) {
        try {
            Path target = rootDir.resolve(path);
            return Files.newInputStream(target);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    @Override
    public boolean delete(String path) {
        try {
            Path target = rootDir.resolve(path);
            return Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    @Override
    public boolean exists(String path) {
        Path target = rootDir.resolve(path);
        return Files.exists(target);
    }

    // 🔐 basic sanitization
    String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-_/]", "_");
    }
}