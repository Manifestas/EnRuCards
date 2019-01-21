package dev.manifest.en_rucards.di.module;

import android.content.Context;
import android.media.AudioManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.en_rucards.data.storage.FileStorage;
import dev.manifest.en_rucards.data.storage.SoundFileStorage;

@Module
public class MediaModule {

    private Context context;

    public MediaModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    AudioManager provideAudioManager() {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides
    @Singleton
    FileStorage provideSoundFileStorage() {
        return new SoundFileStorage(context);
    }
}
