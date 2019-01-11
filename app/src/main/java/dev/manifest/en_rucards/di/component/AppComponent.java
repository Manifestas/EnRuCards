package dev.manifest.en_rucards.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dev.manifest.en_rucards.di.module.AppModule;
import dev.manifest.en_rucards.di.module.NetModule;
import dev.manifest.en_rucards.di.module.SharedPreferenceModule;
import dev.manifest.en_rucards.words.WordsFragment;

@Singleton
@Component(modules = {AppModule.class, SharedPreferenceModule.class, NetModule.class})
public interface AppComponent {

    void injectInto(Application holder);
    void injectInto(WordsFragment wordsFragment);
}
