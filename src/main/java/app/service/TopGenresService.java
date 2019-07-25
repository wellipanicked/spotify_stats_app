package app.service;

import app.model.TopGenreModel;

import java.util.List;

public interface TopGenresService {

    List<TopGenreModel> placeList(List<TopGenreModel> list, int place);
    List<TopGenreModel> getTopGenres();
}
