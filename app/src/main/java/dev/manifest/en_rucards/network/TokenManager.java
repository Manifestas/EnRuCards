package dev.manifest.en_rucards.network;

public interface TokenManager {

    String getToken();

    boolean hasToken();

    void clearToken();

    String refreshToken();
}
