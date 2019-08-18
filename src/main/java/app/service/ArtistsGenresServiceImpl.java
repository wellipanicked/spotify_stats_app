package app.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistsGenresServiceImpl implements ArtistsGenresService {

    @Autowired
    FollowedArtistsServiceImpl followedArtistsService;

    @Autowired
    SpotifyApiService spotifyApiService;

    @Override
    public Multimap<String, String> getArtistsGenresMultimap() {
        return createArtistsGenresMultimap();
    }

    private Multimap<String, String> createArtistsGenresMultimap() {

        Artist[] followedArtists = followedArtistsService.getFollowedArtists(spotifyApiService.getSpotifyApi());
        Multimap<String, String> artistsGenresMultimap = ArrayListMultimap.create();
        artistsGenresMultimap.clear();

        Arrays.stream(followedArtists)
                .forEach(followedArtist ->
                        Arrays.stream(followedArtist.getGenres())
                                .forEach(genre ->
                                        artistsGenresMultimap.put(genre, followedArtist.getName())));

        return artistsGenresMultimap;
    }

    @Override
    public Map<String, Integer> getGenresCount(Multimap<String, String> artGenMultimap) {
        return countGenres(artGenMultimap);
    }

    private Map<String, Integer> countGenres(Multimap<String, String> artGenMultimap) {
        Map<String, Integer> genresCountMap = new HashMap<>();

        for (String key : artGenMultimap.keySet()) {
            genresCountMap.put(key, artGenMultimap.get(key).size());
        }
        return genresCountMap;
    }

    @Override
    public List<String> getGenreArtists(Multimap<String, String> artistsGenresMultimap, String genre) {
        return findGenreArtists(artistsGenresMultimap, genre);
    }

    private List<String> findGenreArtists(Multimap<String, String> artistsGenresMultimap, String genre) {
        List<String> genreArtists;
        genreArtists = new ArrayList<>(artistsGenresMultimap.get(genre));
        return genreArtists;
    }

}
