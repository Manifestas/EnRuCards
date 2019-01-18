package dev.manifest.en_rucards.network;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class TokenInterceptor implements Interceptor {

    private static final String TAG = TokenInterceptor.class.getSimpleName();

    private TokenManager tokenManager;

    @Inject
    public TokenInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.header("Authorization") != null) {
            Log.d(TAG, "intercept: already have header Authorization.");
            return chain.proceed(originalRequest);
        }

        String accessToken = tokenManager.getToken();
        Log.d(TAG, "intercept: token = " + accessToken);

        Request newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .build();
        return chain.proceed(newRequest);
    }
}
