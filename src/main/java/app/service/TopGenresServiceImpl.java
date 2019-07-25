package app.service;

import app.model.TopGenreModel;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopGenresServiceImpl implements TopGenresService {

    @Autowired
    ArtistsGenresServiceImpl artistsGenresService;

    private Integer getMax(Map<String, Integer> genresCountMap) {
        return genresCountMap.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();
    }

    private List<String> getListOfMaxes(Map<String, Integer> genresCountMap, Integer max) {
        return genresCountMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(max))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<TopGenreModel> createTopGenresList() {

        List<TopGenreModel> genresList = new ArrayList<>();
        Multimap<String, String> artistsGenresMultimap = artistsGenresService.getArtistsGenresMultimap();
        Map<String, Integer> genresCountMap = artistsGenresService.getGenresCount(artistsGenresMultimap);

        for (int k = 0; k < 3; k++) {
            final Integer p = k + 1;

            if (!genresCountMap.isEmpty()) {
                getListOfMaxes(genresCountMap, getMax(genresCountMap)).forEach(e -> {
                    TopGenreModel topGenreModel = new TopGenreModel(p, e, artistsGenresService.getGenreArtists(artistsGenresMultimap, e));
                    genresList.add(topGenreModel);
                    genresCountMap.remove(e);
                });
            } else {
                break;
            }
        }
        return genresList;
    }

    @Override
    public List<TopGenreModel> placeList(List<TopGenreModel> list, int place) {
        return list.stream()
                .filter(e -> e.getPlace() == place)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopGenreModel> getTopGenres() {
        return createTopGenresList();
    }
}
