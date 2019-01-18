package dev.manifest.en_rucards.di.component;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import dev.manifest.en_rucards.di.module.AppModule;
import dev.manifest.en_rucards.di.module.CardsModule;
import dev.manifest.en_rucards.di.module.NetModule;
import dev.manifest.en_rucards.network.LingvoTokenManager;
import dev.manifest.en_rucards.words.CardsFragment;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class,
        NetModule.class,
        CardsModule.class})
public interface AppComponent {

    void injectInto(Application holder);

    void injectInto(CardsFragment cardsFragment);

    LingvoTokenManager getLingvoTokenManager();

    SharedPreferences getSharedPreference();

    @Named("auth")
    Retrofit getAuthRetrofit();

    @Named("non_auth")
    Retrofit getRetrofit();
}
