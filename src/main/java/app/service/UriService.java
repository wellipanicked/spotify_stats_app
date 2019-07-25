package app.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.URI;

@Service
public class UriService {

    private AuthorizationCodeUriRequest makeAuthorizationCodeUriRequest(SpotifyApi spotifyApi) {
        return spotifyApi.authorizationCodeUri()
                .state("x4xkmn9pu3j6ukrs8n")
                .scope("user-read-birthdate,user-read-email, user-read-private, user-follow-read")
                .show_dialog(true)
                .build();

    }

    private URI createUri(SpotifyApi spotifyApi) {
        return makeAuthorizationCodeUriRequest(spotifyApi).execute();
    }

    public boolean openWebpage(SpotifyApi spotifyApi) {

        Desktop desktop = Desktop.getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(createUri(spotifyApi));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
