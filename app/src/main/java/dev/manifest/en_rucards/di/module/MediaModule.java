package dev.manifest.en_rucards.di.module;

import android.content.Context;
import android.media.AudioManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
