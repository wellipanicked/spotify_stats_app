package app.service;

import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.*;

@Service
public class ArtistsGenresService {

    private Map<String, Integer> genresMap = new HashMap<>();

    public Map<String, Integer> getGenresMap(Artist[] art) {

        genresMap.clear();

        for (int k = 0; k < art.length; k++) {
            for (int l = 0; l < art[k].getGenres().length; l++) {

                String key = art[k].getGenres()[l];
                if (genresMap.containsKey(key)) {
                    genresMap.put(key, genresMap.get(key) + 1);
                } else {
                    genresMap.put(key, 1);
                }
            }
        }
        return genresMap;
    }

    //lista gatunków z ilością zespołów
    public void showGenresInNumbers(Map<String, Integer> genresMap) {

        genresMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    public Map<String, Integer> getGenresMap() {
        return genresMap;
    }
}
