package dev.manifest.en_rucards.di.component;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import dev.manifest.en_rucards.di.module.AppModule;
import dev.manifest.en_rucards.di.module.CardsModule;
import dev.manifest.en_rucards.di.module.MediaModule;
import dev.manifest.en_rucards.di.module.NetModule;
import dev.manifest.en_rucards.cards.CardsFragment;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class,
        NetModule.class,
        MediaModule.class,
        CardsModule.class})
public interface AppComponent {

    void injectInto(CardsFragment cardsFragment);

    @Named("auth")
    Retrofit getAuthRetrofit();

    @Named("non_auth")
    Retrofit getRetrofit();
}
