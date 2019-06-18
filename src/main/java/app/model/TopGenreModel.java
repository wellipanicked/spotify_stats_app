package app.model;

import java.util.List;

public class TopGenreModel {

    private int place;
    private String genreName;

    private List<String> artists;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void printTopGenre()
    {
        System.out.println(getPlace());
        System.out.println(getGenreName());
    }
}
