package app.service;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Artist;

public interface FollowedArtistsService {

    Artist[] getFollowedArtists(SpotifyApi spotifyApi);
}
