package dev.manifest.en_rucards.network;

import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

@Singleton
public class TokenAuthenticator implements Authenticator {

    private static final String TAG = TokenAuthenticator.class.getSimpleName();

    private TokenManager tokenManager;

    @Inject
    public TokenAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {

        String newAccessToken = tokenManager.refreshToken();
        if (newAccessToken != null && !newAccessToken.isEmpty()) {
            Log.d(TAG, "authenticate: new token = " + newAccessToken);
            //refresh is successful
            // make current request with new access token
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .build();

        } else {
            // refresh failed returning null is critical here, because if you don't return null
            // it will try to refresh token continuously like 1000 times.
            // also you can try 2-3-4 times
            Log.d(TAG, "authenticate: new token null or empty.");
            return null;
        }
    }
}
