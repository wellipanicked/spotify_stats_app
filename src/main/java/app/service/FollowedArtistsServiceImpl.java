package app.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.PagingCursorbased;
import com.wrapper.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FollowedArtistsServiceImpl implements FollowedArtistsService {

@Autowired
    SpotifyApiService spotifyApiService;

    @Override
    public Artist[] getFollowedArtists(SpotifyApi spotifyApi){
        return createFollowedArtistsArray(spotifyApi);
    }

    private Artist[] createFollowedArtistsArray(SpotifyApi spotifyApi) {
        ModelObjectType type = ModelObjectType.ARTIST;
        Artist[] followedArtists = null;
        final PagingCursorbased<Artist> artistPagingCursorbased;

        final GetUsersFollowedArtistsRequest usersFollowedArtistsRequest = spotifyApi
                .getUsersFollowedArtists(type)
                .limit(50)
                .build();
        try {
            artistPagingCursorbased = usersFollowedArtistsRequest.execute();
            followedArtists = artistPagingCursorbased.getItems();

        } catch (IOException | SpotifyWebApiException e) {

            System.out.println("Error: " + e.getMessage());
        }

        return followedArtists;
    }



}