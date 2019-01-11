package dev.manifest.en_rucards.network;

import android.content.SharedPreferences;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Response;

public class LingvoTokenManager implements TokenManager {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String TOKEN_EXPIRE_TIME = "tokenExpireTime";
    public static final int DAYS_BEFORE_TOKEN_EXPIRES = 1;

    @Inject
    SharedPreferences sharedPreferences;
    private String token;
    private long expireTime;

    @Override
    public String getToken() {
        if (!hasToken()) {
            token = sharedPreferences.getString(ACCESS_TOKEN, null);
            //get expire time from shared preferences
            expireTime = sharedPreferences.getLong(TOKEN_EXPIRE_TIME, 0);
            Calendar calendar = Calendar.getInstance();
            Date nowDate = calendar.getTime();

            calendar.setTimeInMillis(expireTime);
            Date expireDate = calendar.getTime();

            int result = nowDate.compareTo(expireDate);
        /* when comparing dates -1 means date passed so we need to refresh token
        see {@link Date#compareTo} */
            if (result < 0) {
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
        saveTokenToPreference(null, 0);
    }

    @Override
    public String refreshToken() {
        Response<String> tokenResponse;
        try {
            tokenResponse = NetworkService.getInstance().refreshToken();
        } catch (IOException e) {
            return null;
        }
        String newAccessToken = tokenResponse.body();
        if (tokenResponse.isSuccessful() && newAccessToken != null) {
            token = newAccessToken;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, DAYS_BEFORE_TOKEN_EXPIRES);
            expireTime = calendar.getTimeInMillis();
            saveTokenToPreference(newAccessToken, expireTime);
        }
        return newAccessToken;
    }

    private void saveTokenToPreference(String token, long expireTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, token);
        editor.putLong(TOKEN_EXPIRE_TIME, expireTime);
        editor.apply();
    }
}
