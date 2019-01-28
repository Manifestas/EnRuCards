package dev.manifest.en_rucards.di.module;

import android.content.SharedPreferences;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dev.manifest.en_rucards.BuildConfig;
import dev.manifest.en_rucards.network.LingvoTokenManager;
import dev.manifest.en_rucards.network.TokenAuthenticator;
import dev.manifest.en_rucards.network.TokenInterceptor;
import dev.manifest.en_rucards.network.TokenManager;
import dev.manifest.en_rucards.util.AppExecutors;
import dev.manifest.en_rucards.util.schedulers.SchedulerProvider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetModule {

    private static final int THREAD_COUNT = 3;

    @Provides
    @Singleton
    TokenManager provideLingvoTokenManager(SharedPreferences preferences,
                                           @Named("non_auth") Retrofit retrofit) {
        return new LingvoTokenManager(preferences, retrofit);
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
    OkHttpClient provideAuthOkHttpClient(HttpLoggingInterceptor interceptor,
                                         TokenInterceptor tokenInterceptor,
                                         TokenAuthenticator authenticator) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.authenticator(authenticator);
        client.addInterceptor(tokenInterceptor);
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Named("non_auth")
    @Singleton
    Retrofit provideRetrofit(@Named("non_auth") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvide() {
        return new SchedulerProvider();
    }
}
