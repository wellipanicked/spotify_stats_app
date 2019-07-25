package app.service;

import com.wrapper.spotify.SpotifyApi;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class SpotifyApiService {

    private static SpotifyApi spotifyApi;

    public static SpotifyApi initializeSpotifyApi(String clientId, String clientSecret, URI redirectUri) {
         spotifyApi = new SpotifyApi.Builder()
                 .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();

         return spotifyApi;
}

    public SpotifyApi getSpotifyApi() {

        return spotifyApi;
    }
}