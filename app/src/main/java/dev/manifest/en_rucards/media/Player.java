package dev.manifest.en_rucards.media;

import java.io.IOException;

public interface Player {

    void play(String path) throws IOException;

    void stop();
}
