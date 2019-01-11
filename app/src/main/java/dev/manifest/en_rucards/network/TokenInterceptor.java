package dev.manifest.en_rucards.network;

import android.content.SharedPreferences;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Inject
    SharedPreferences sharedPreferences;
    LingvoTokenManager tokenManager = new LingvoTokenManager();

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.header("Authorization") != null) {
            return chain.proceed(originalRequest);
        }

        String accessToken = tokenManager.getToken();
        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }
        Request newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(newRequest);
    }
}
