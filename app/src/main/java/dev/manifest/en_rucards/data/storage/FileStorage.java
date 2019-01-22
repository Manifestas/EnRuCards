package dev.manifest.en_rucards.data.storage;

import java.io.InputStream;

public interface FileStorage {

    String getFilePath(String fileName);

    boolean saveFile(String fileName, InputStream inputStream);
}
