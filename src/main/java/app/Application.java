package app;

import app.controller.TokenController;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;

import static app.controller.TokenController.getCode;
import static app.service.UriService.authorizationCodeUri;
import static app.service.UriService.openWebpage;


@SpringBootApplication
public class Application implements CommandLineRunner {


    private static final String CLIENTID = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final String CLIENTSECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/logged/");


    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENTID)
            .setClientSecret(CLIENTSECRET)
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

        openWebpage(u);

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

    }

}




