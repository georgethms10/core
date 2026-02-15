package com.financeme.core.storage.filesystem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LocalFileSystemStorageClientTest {

    private final Path tempDir;

    LocalFileSystemStorageClientTest() throws Exception {
        tempDir = Files.createTempDirectory("storage-test");
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.walk(tempDir)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (Exception ignored) {}
                });
    }

    @Test
    void shouldUploadAndExist() {
        LocalFileSystemStorageClient client =
                new LocalFileSystemStorageClient(tempDir);

        InputStream data = new ByteArrayInputStream("hello".getBytes());

        String path = client.upload("test.txt", data);

        assertTrue(client.exists(path));
    }

    @Test
    void shouldDeleteFile() {
        LocalFileSystemStorageClient client =
                new LocalFileSystemStorageClient(tempDir);

        InputStream data = new ByteArrayInputStream("hello".getBytes());
        String path = client.upload("delete.txt", data);

        assertTrue(client.delete(path));
        assertFalse(client.exists(path));
    }

    @Test
    void shouldSanitizeFileName() {
        LocalFileSystemStorageClient client =
                new LocalFileSystemStorageClient(tempDir);

        String sanitized = client.sanitizeFileName("file@#$%.txt");

        assertEquals("file____.txt", sanitized);
    }
}