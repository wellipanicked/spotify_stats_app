package app.service;

import app.model.TopGenreModel;
import com.wrapper.spotify.model_objects.specification.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopGenresService {

    private Map<String, Integer> genresMap;
    private

    @Autowired
    FollowedArtistsService followedArtistsService;

    @Autowired
    ArtistsGenresService artistsGenresService;

    public void init() {
        Artist[] followedArtists;
        followedArtists = followedArtistsService.getFollowedArtists();
        genresMap = artistsGenresService.getGenresCountMap(followedArtists);

    }

    public List<TopGenreModel> getTopGenres() {

        List<TopGenreModel> genresList = new ArrayList<>();
        init();

        for (int k = 0; k < 3; k++) {
            int maxValueInMap = (Collections.max(genresMap.values()));
            for (Map.Entry<String, Integer> entry : genresMap.entrySet()) {
                if (entry.getValue() == maxValueInMap && entry.getValue() != 0) {
                    TopGenreModel topGenreModel = new TopGenreModel(entry.getKey(), artistsGenresService.getGenreArtists(entry.getKey()));
                    genresMap.put(entry.getKey(), 0);

                    if (!genresList.contains(topGenreModel) && topGenreModel.getGenreName() != null) {
                        genresList.add(topGenreModel);
                    }
                }
            }
        }

        return genresList;
    }
}
