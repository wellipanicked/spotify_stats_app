package app;

import com.wrapper.spotify.SpotifyApi;

import java.net.URI;

public class SpotifyApiSingleton {
    private static SpotifyApiSingleton instance;
    private static SpotifyApi spotifyApi;

    private SpotifyApiSingleton(String clientId, String clientSecret, URI redirectUri) {

        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }

    public static SpotifyApiSingleton getInstance(String clientId, String clientSecret, URI redirectUri) {
        if (instance == null) {
            instance = new SpotifyApiSingleton(clientId, clientSecret, redirectUri);
        }
        return instance;
    }

    public static SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }
}