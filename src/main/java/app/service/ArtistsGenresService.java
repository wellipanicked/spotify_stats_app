package app.service;

import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;

public interface ArtistsGenresService {

    Multimap<String, String> getArtistsGenresMultimap();
    Map<String, Integer> getGenresCount(Multimap<String, String> artGenMultimap);
    List<String> getGenreArtists(Multimap<String, String> artistsGenresMultimap, String genre);

}
