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
                for (String genreName : getListOfMaxes(genresCountMap)) {
                    genresList.add(getTopGenreModel(artistsGenresMultimap, genreName, position, counter));
                    genresCountMap.remove(genreName);
                    counter++;
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

    private TopGenreModel getTopGenreModel(Multimap<String, String> artistsGenresMultimap, String genreName, int position, int counter) {
        boolean isFirst = counter == 0;
        return new TopGenreModel(position, isFirst, genreName, artistsGenresService.getGenreArtists(artistsGenresMultimap, genreName));
    }

    public int getActualHighestPlace(List<TopGenreModel> topGenres) {
        TopGenreModel maxPlaceTopGenreModel = topGenres.stream().max(comparing(TopGenreModel::getPlace)).get();
        return maxPlaceTopGenreModel.getPlace();
    }
}
