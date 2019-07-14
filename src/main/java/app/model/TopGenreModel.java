package app.model;

import java.util.Collection;
import java.util.List;

public class TopGenreModel {

    private String genreName;
    private List<String> artists;


    public TopGenreModel(String genreName, List<String> artists) {
        this.genreName = genreName;
        this.artists = artists;
    }

    public String getGenreName() {
        return genreName;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void printTopGenre() {
        System.out.println(getGenreName());
    }
}
