package app.service;

import app.model.TopGenreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopGenresService {

    @Autowired
    ArtistsGenresService artistsGenresService;

    public List<TopGenreModel> getTopGenres(){

        List<TopGenreModel> genresList = new ArrayList<>();
        Map<String, Integer> genresMap = artistsGenresService.getGenresCount();

        for (int k = 0; k < 3; k++) {
            if (genresMap.entrySet().stream().max(Map.Entry.comparingByValue()).isPresent()) {
                Map.Entry<String, Integer> maxEntry = genresMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
                TopGenreModel topGenreModel = new TopGenreModel(maxEntry.getKey(), artistsGenresService.getGenreArtists(maxEntry.getKey()));
                genresList.add(topGenreModel);
                genresMap.remove(maxEntry.getKey());
            }
            else{
                break;
            }
        }
        return genresList;
    }
}
