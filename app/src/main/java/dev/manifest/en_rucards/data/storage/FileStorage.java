package dev.manifest.en_rucards.data.storage;

import java.io.InputStream;

public interface FileStorage {

    InputStream getFile(String path, String fileName);

    boolean saveFile(String path, String fileName, InputStream inputStream);
}
