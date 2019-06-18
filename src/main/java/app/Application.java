package app;

import app.controller.TokenController;
import app.service.ArtistsGenresService;
import app.service.FollowedArtistsService;
import app.service.UriService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;

import static app.controller.TokenController.getCode;
import static app.service.UriService.authorizationCodeUri;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final String clientId = "111111111111111111111";
    private static final String clientSecret = "111111111111111111";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/logged/");


    @Autowired
    private FollowedArtistsService followedArtistsService;

    @Autowired
    private ArtistsGenresService artistsGenresService;

    @Autowired
    private UriService uriService;


    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();



    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }



    @Override
    public void run(String... args) throws Exception {
        URI u = authorizationCodeUri();

        uriService.openWebpage(u);

        while (getCode() == null) {
            Thread.sleep(500);
        }

        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(TokenController.getCode())
                .build();

        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

        // Set access and refresh token for further "spotifyApi" object usage
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

        String token = authorizationCodeCredentials.getAccessToken();
        System.out.println(token);

//        Artist[] follows = followedArtistsService.getFollowedArtists();

//        followedArtistsService.printFollowedArtistsDetailed(follows);

//        Map<String, Integer> genreCount = artistsGenresService.getGenresMap(follows);
    }

}




