package app.service;

import app.model.TopGenreModel;

import java.util.List;

public interface TopGenresService {

    List<TopGenreModel> getTopGenres(int howManyTops);
}
