package app;

import app.controller.TokenController;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;

import static app.SpotifyApiSingleton.getSpotifyApi;
import static app.controller.TokenController.getCode;
import static app.service.UriService.authorizationCodeUri;
import static app.service.UriService.openWebpage;


@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/logged/");


    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        if(args[0] == null || args[0].length() != 32 || args[1] == null || args[1].length() != 32){
            System.out.println("Niepoprawne dane wejściowe");
            System.exit(-1);
        }
        else {

            SpotifyApiSingleton spotifyApiSingleton = SpotifyApiSingleton.getInstance(args[0], args[1], redirectUri);

            URI u = authorizationCodeUri();

            openWebpage(u);

            while (getCode() == null) {
                Thread.sleep(500);
            }

            final AuthorizationCodeRequest authorizationCodeRequest = getSpotifyApi().authorizationCode(TokenController.getCode())
                    .build();

            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            getSpotifyApi().setAccessToken(authorizationCodeCredentials.getAccessToken());
            getSpotifyApi().setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

            String token = authorizationCodeCredentials.getAccessToken();
            System.out.println(token);
        }
    }

}




