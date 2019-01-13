package dev.manifest.en_rucards.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.en_rucards.BuildConfig;
import dev.manifest.en_rucards.network.LingvoTokenManager;
import dev.manifest.en_rucards.network.TokenAuthenticator;
import dev.manifest.en_rucards.network.TokenInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    LingvoTokenManager provideLingvoTokenManager() {
        return new LingvoTokenManager();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Named("auth")
    @Singleton
    OkHttpClient provideAuthOkHttpClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.authenticator(new TokenAuthenticator());
        client.addInterceptor(new TokenInterceptor());
        client.addInterceptor(interceptor);
        return client.build();
    }

    @Provides
    @Named("non_auth")
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return client.build();
    }

    @Provides
    @Named("auth")
    @Singleton
    Retrofit provideAuthRetrofit(@Named("auth") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Named("non_auth")
    @Singleton
    Retrofit provideRetrofit(@Named("non_auth") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
