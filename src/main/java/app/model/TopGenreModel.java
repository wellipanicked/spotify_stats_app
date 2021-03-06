package app.model;

import java.util.List;

public class TopGenreModel {

    private int place;
    private boolean isFirst;
    private String genreName;
    private List<String> artists;


    public TopGenreModel(int place, boolean isFirst, String genreName, List<String> artists) {
        this.place = place;
        this.isFirst = isFirst;
        this.genreName = genreName;
        this.artists = artists;
    }

    public int getPlace() {
        return place;
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

    public boolean isFirst() {
        return isFirst;
    }

}

