package dev.manifest.en_rucards.di.component;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dev.manifest.en_rucards.di.module.AppModule;
import dev.manifest.en_rucards.di.module.NetModule;
import dev.manifest.en_rucards.di.module.PresenterModule;
import dev.manifest.en_rucards.di.module.SharedPreferenceModule;
import dev.manifest.en_rucards.network.LingvoTokenManager;
import dev.manifest.en_rucards.words.WordsFragment;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, SharedPreferenceModule.class, NetModule.class, PresenterModule.class})
public interface AppComponent {

    void injectInto(Application holder);

    void injectInto(WordsFragment wordsFragment);

    LingvoTokenManager getLingvoTokenManager();

    SharedPreferences getSharedPreference();

    Retrofit getRetrofit();
}
