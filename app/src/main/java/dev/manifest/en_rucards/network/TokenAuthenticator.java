package dev.manifest.en_rucards.network;

import java.io.IOException;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Inject
    LingvoTokenManager tokenManager;

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {

        String newAccessToken = tokenManager.refreshToken();
        if (newAccessToken != null && !newAccessToken.isEmpty()) {
            //refresh is successful
            // make current request with new access token
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .build();

        } else {
            // refresh failed returning null is critical here, because if you don't return null
            // it will try to refresh token continuously like 1000 times.
            // also you can try 2-3-4 times
            return null;
        }
    }
}
