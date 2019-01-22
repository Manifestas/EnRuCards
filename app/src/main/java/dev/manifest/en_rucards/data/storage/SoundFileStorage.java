package dev.manifest.en_rucards.data.storage;

import android.content.Context;
import android.util.Base64;
import android.util.Base64InputStream;

import java.io.File;
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
    public String getFilePath(String fileName) {
        File directory = context.getDir(DIR_NAME, Context.MODE_PRIVATE);
        File soundFile = new File(directory, fileName);
        if (soundFile.exists()) {
            return soundFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    @Override
    public boolean saveFile(String fileName, InputStream inputStream) {
        File directory = context.getDir(DIR_NAME, Context.MODE_PRIVATE);
        File soundFile = new File(directory, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(soundFile);
             // response is Base64 file, decode it
             Base64InputStream base64InputStream = new Base64InputStream(inputStream, Base64.DEFAULT)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = base64InputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
