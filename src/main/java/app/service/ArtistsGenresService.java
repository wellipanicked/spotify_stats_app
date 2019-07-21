package app.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArtistsGenresService {

    @Autowired
    FollowedArtistsService followedArtistsService;

    private Map<String, Integer> genresCountMap = new HashMap<>();

    private static Multimap<String, String> artistsGenresMultimap = ArrayListMultimap.create();

    public void makeArtistsGenresMultimap() {

        artistsGenresMultimap.clear();

        Artist[] followedArtists = followedArtistsService.getFollowedArtists();

        Arrays.stream(followedArtists)
                .forEach(followedArtist ->
                        Arrays.stream(followedArtist.getGenres())
                                .forEach(genre ->
                                        artistsGenresMultimap.put(genre, followedArtist.getName())));
    }

    public Map<String, Integer> getGenresCount() {

        makeArtistsGenresMultimap();
        genresCountMap.clear();

        for (String key : artistsGenresMultimap.keySet()) {
            genresCountMap.put(key, artistsGenresMultimap.get(key).size());
        }
        return genresCountMap;
    }

    public List<String> getGenreArtists(String genre) {

        List<String> genreArtists;
        genreArtists = new ArrayList<>(artistsGenresMultimap.get(genre));
        return genreArtists;
    }


    public Map<String, Integer> getGenresCountMap() {
        return genresCountMap;
    }
}
