package dev.manifest.en_rucards.data.storage;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundFileStorage implements FileStorage {

    public static final String DIR_NAME = "sound";

    private Context context;

    public SoundFileStorage(Context context) {
        this.context = context;
    }

    @Override
    public InputStream getFile(String path, String fileName) {
        File directory = context.getDir(DIR_NAME, Context.MODE_PRIVATE);
        File soundFile = new File(directory, fileName);
        if (soundFile.exists()) {
            try {
                return new FileInputStream(soundFile);
            } catch (FileNotFoundException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean saveFile(String path, String fileName, InputStream inputStream) {
        File directory = context.getDir(DIR_NAME, Context.MODE_PRIVATE);
        File soundFile = new File(directory, fileName);
        try {
            try (FileOutputStream outputStream = new FileOutputStream(soundFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
