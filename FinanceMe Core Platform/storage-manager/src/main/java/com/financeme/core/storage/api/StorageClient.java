package com.financeme.core.storage;

import java.io.InputStream;

public interface StorageClient {

    String upload(String path, InputStream inputStream);

    InputStream download(String path);

    boolean delete(String path);

    boolean exists(String path);
}