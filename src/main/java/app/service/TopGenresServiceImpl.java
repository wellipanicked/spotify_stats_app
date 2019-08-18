package app.service;

import app.model.TopGenreModel;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class TopGenresServiceImpl implements TopGenresService {

    @Autowired
    ArtistsGenresServiceImpl artistsGenresService;

    @Override
    public List<TopGenreModel> getTopGenres(int howManyTops) {
        return createTopGenresList(howManyTops);
    }

    private List<TopGenreModel> createTopGenresList(int howManyTops) {

        List<TopGenreModel> genresList = new ArrayList<>();
        Multimap<String, String> artistsGenresMultimap = artistsGenresService.getArtistsGenresMultimap();
        Map<String, Integer> genresCountMap = artistsGenresService.getGenresCount(artistsGenresMultimap);

        for (int k = 0; k < howManyTops; k++) {
            final Integer position = k + 1;
            int counter = 0;

            if (!genresCountMap.isEmpty()) {
                for (String e : getListOfMaxes(genresCountMap)) {
                    TopGenreModel topGenreModel;
                    if (counter == 0) {
                        topGenreModel = new TopGenreModel(position, true, e, artistsGenresService.getGenreArtists(artistsGenresMultimap, e));
                    } else {
                        topGenreModel = new TopGenreModel(position, false, e, artistsGenresService.getGenreArtists(artistsGenresMultimap, e));
                    }
                    counter++;
                    genresList.add(topGenreModel);
                    genresCountMap.remove(e);
                }
            } else {
                break;
            }
        }
        return genresList;
    }

    private List<String> getListOfMaxes(Map<String, Integer> genresCountMap) {
        return genresCountMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(getMax(genresCountMap)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Integer getMax(Map<String, Integer> genresCountMap) {
        return genresCountMap.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();
    }

    public int getActualPlacesMax(List<TopGenreModel> topGenres) {
        TopGenreModel maxPlaceTopGenreModel = topGenres.stream().max(comparing(TopGenreModel::getPlace)).get();
        return maxPlaceTopGenreModel.getPlace();
    }
}
