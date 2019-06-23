package app.service;

import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.PagingCursorbased;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static app.Application.spotifyApi;

@Service
public class FollowedArtistsService {

    private static final ModelObjectType type = ModelObjectType.ARTIST;
    private Artist[] followedArtists;


    public Artist[] getFollowedArtists() {
        final GetUsersFollowedArtistsRequest usersFollowedArtistsRequest = spotifyApi
                .getUsersFollowedArtists(type)
                .limit(50)
                .build();
        try {
            final PagingCursorbased<Artist> artistPagingCursorbased = usersFollowedArtistsRequest.execute();
            followedArtists = artistPagingCursorbased.getItems();

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return followedArtists;
    }

}
