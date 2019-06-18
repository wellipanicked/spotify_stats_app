package app.service;

import app.model.TopGenreModel;
import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopGenresService {

    @Autowired
    FollowedArtistsService followedArtistsService;
    @Autowired
    ArtistsGenresService artistsGenresService;

    public List<TopGenreModel> showTopGenres() {
        Artist[] followedArtists = followedArtistsService.getFollowedArtists();
        List<TopGenreModel> genresList = new ArrayList<>();
        Map<String, Integer> genresMap = artistsGenresService.getGenresMap(followedArtists);

        for (int k = 0; k < 3; k++) {
            TopGenreModel topGenreModel = new TopGenreModel();
            int maxValueInMap = (Collections.max(genresMap.values()));
            for (Map.Entry<String, Integer> entry : genresMap.entrySet()) {
                if (entry.getValue() == maxValueInMap){ // && (genresList.isEmpty() || !containsName(genresList, entry.getKey()))) {
                    topGenreModel.setPlace(k);
                    topGenreModel.setGenreName(entry.getKey());
                    topGenreModel.setArtists(new ArrayList<>());
                    genresMap.put(entry.getKey(), 0);

                    String currentGenre = entry.getKey();
                    for (int m = 0; m < followedArtists.length; m++) {
                        for (int n = 0; n < followedArtists[m].getGenres().length; n++) {
                            if (followedArtists[m].getGenres()[n].equals(currentGenre)) {
                                topGenreModel.getArtists().add(followedArtists[m].getName());
                            }

                        }
                    }
                }
            }
            genresList.add(topGenreModel);
        }

        return genresList;
    }
}
