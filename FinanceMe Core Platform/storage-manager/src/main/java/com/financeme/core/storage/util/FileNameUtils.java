package com.financeme.core.storage.util;

public final class FileNameUtils {

    private FileNameUtils() {}

    public static String sanitize(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Invalid file name");
        }

        // Prevent path traversal
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
    }
}