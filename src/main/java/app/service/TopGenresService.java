package app.service;

import app.model.TopGenreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopGenresService {

    @Autowired
    ArtistsGenresService artistsGenresService;

    private List<TopGenreModel> genresList = new ArrayList<>();

    public List<TopGenreModel> getGenresList() {
        genresList.clear();
        getTopGenres(getGenresMap());
        return genresList;
    }

    public Map<String, Integer> getGenresMap() {
        return artistsGenresService.getGenresCount();
    }

    public List<TopGenreModel> getTopGenres(Map<String, Integer> genresMap){

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
