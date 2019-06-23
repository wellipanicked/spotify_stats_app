package app.service;

import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ArtistsGenresService {

    private Map<String, Integer> genresCountMap = new HashMap<>();


    @Autowired
    FollowedArtistsService followedArtistsService;


    public List<String> getGenreArtists(String genre){
        List<String> genreArtists = new ArrayList<>();

        Artist[] followedArtists = followedArtistsService.getFollowedArtists();

        for (Artist artist : followedArtists){
            for(String gen : artist.getGenres()){
                if(gen.equals(genre)){
                    genreArtists.add(artist.getName());
                }
            }
        }

        return genreArtists;
    }




    public Map<String, Integer> getGenresCountMap(Artist[] art) {

        genresCountMap.clear();

        List<Artist> artList = Arrays.asList(art);

        artList.stream().forEach(artist ->
        { String[] genres = artist.getGenres();
            List<String> genList = Arrays.asList(genres);
            genList.stream().forEach(genre -> {
                if (genresCountMap.containsKey(genre)) {
                    genresCountMap.put(genre, genresCountMap.get(genre) + 1);
                } else {
                    genresCountMap.put(genre, 1);
                }
            });
        });


        return genresCountMap;
    }

    public Map<String, Integer> getGenresCountMap() {
        return genresCountMap;
    }
}
