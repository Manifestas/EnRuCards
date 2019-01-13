package dev.manifest.en_rucards.network;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import dev.manifest.en_rucards.App;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LingvoTokenManager implements TokenManager {
    
    private static final String TAG = LingvoTokenManager.class.getSimpleName();

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String TOKEN_EXPIRE_TIME = "tokenExpireTime";
    public static final int DAYS_BEFORE_TOKEN_EXPIRES = 1;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    @Named("non_auth")
    Retrofit retrofit;

    private String token;
    private long expireTime;

    public LingvoTokenManager() {
        sharedPreferences = App.getAppComponent().getSharedPreference();
        retrofit = App.getAppComponent().getRetrofit();
    }

    @Override
    public String getToken() {
        if (!hasToken()) {
            Log.d(TAG, "has no token.");
            token = sharedPreferences.getString(ACCESS_TOKEN, null);
            //get expire time from shared preferences
            expireTime = sharedPreferences.getLong(TOKEN_EXPIRE_TIME, 0);
            //if token not saved in preferences.
            if (token == null) {
                Log.d(TAG, "getToken: token not saved in sharedPreference.");
                //refresh token here , and got new access token
                token = refreshToken();
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            Date nowDate = calendar.getTime();

            calendar.setTimeInMillis(expireTime);
            Date expireDate = calendar.getTime();

            int result = nowDate.compareTo(expireDate);
            /* When comparing dates -1 means date passed
            so we need to refresh token see {@link Date#compareTo} */
            if (result < 0) {
                Log.d(TAG, "getToken: token expired.");
                //refresh token here , and got new access token
                token = refreshToken();
            }
        }
        return token;
    }

    @Override
    public boolean hasToken() {
        return token != null && !token.isEmpty();
    }

    @Override
    public void clearToken() {
        token = null;
        expireTime = 0;
        saveTokenToPreference(null, 0);
    }

    @Override
    public String refreshToken() {
        Response<String> tokenResponse;
        try {
            Log.d(TAG, "refreshToken: get new token from api.");
            tokenResponse = retrofit.create(LingvoApi.class).getAuthToken().execute();
        } catch (IOException e) {
            Log.d(TAG, "refreshToken: " + e);
            return null;
        }
        String newAccessToken = tokenResponse.body();
        if (tokenResponse.isSuccessful() && newAccessToken != null) {
            Log.d(TAG, "refreshToken: request successful.");
            token = newAccessToken;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, DAYS_BEFORE_TOKEN_EXPIRES);
            expireTime = calendar.getTimeInMillis();
            saveTokenToPreference(newAccessToken, expireTime);
        }
        return newAccessToken;
    }

    private void saveTokenToPreference(String token, long expireTime) {
        Log.d(TAG, "saveTokenToPreference: token = " + token + "\nexpireTIme = " + expireTime);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.putLong(TOKEN_EXPIRE_TIME, expireTime);
        editor.apply();
    }
}
