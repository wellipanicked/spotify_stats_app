package app.service;

import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.URI;
import java.net.URL;

import static app.Application.spotifyApi;

@Service
public class UriService {

    public static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .state("x4xkmn9pu3j6ukrs8n")
            .scope("user-read-birthdate,user-read-email, user-read-private, user-follow-read")
            .show_dialog(true)
            .build();

    public static URI authorizationCodeUri() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
        return uri;
    }



    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
