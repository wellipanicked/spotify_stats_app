package app.service;

import app.model.TopGenreModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopGenresServiceTest {

    static TopGenreModel topGenreModel;
    static Map<String, Integer> typicalGenresMap = new HashMap<>();
    static Map<String, Integer> smallGenresMap = new HashMap<>();
    static Map<String, Integer> emptyGenresMap = new HashMap<>();
    static Map<String, Integer> equalGenresMap = new HashMap<>();
    static List<String> artistsList = new ArrayList<>();


    @Mock
    @Resource
    static ArtistsGenresService artistsGenresService;

    @InjectMocks
    @Autowired
    TopGenresService topGenresService;

    @BeforeAll
    static void setup() {

        topGenreModel = new TopGenreModel("Test Genre", new ArrayList<>());

        artistsList.add("unknown");

        topGenreModel.getArtists().add("Artist 1");
        topGenreModel.getArtists().add("Artist 2");
        topGenreModel.getArtists().add("Artist 3");

        typicalGenresMap.put("genre 1", 1);
        typicalGenresMap.put("genre 2", 3);
        typicalGenresMap.put("genre 3", 5);
        typicalGenresMap.put("genre 4", 5);
        typicalGenresMap.put("genre 5", 7);
        typicalGenresMap.put("genre 6", 6);

        smallGenresMap.put("genre 1", 1);

        equalGenresMap.put("genre 1", 7);
        equalGenresMap.put("genre 2", 7);
        equalGenresMap.put("genre 3", 7);
        equalGenresMap.put("genre 4", 7);
        equalGenresMap.put("genre 5", 7);
    }


    @Test
    void shouldContainAddedArtist() {
        assertTrue(topGenreModel.getArtists().contains("Artist 1"));
    }

    @Test
    void shouldntContainNotAddedArtist() {
        assertFalse(topGenreModel.getArtists().contains("Artist 4"));
    }

    @Test
    void shouldReturnEmptyList() {
        assertTrue(topGenresService.getTopGenres(emptyGenresMap).isEmpty());
    }

    @Test
    void shouldReturnThreeElements() {
        when(artistsGenresService.getGenreArtists(anyString())).thenReturn(artistsList);
        assertTrue(topGenresService.getTopGenres(typicalGenresMap).size() == 3);
    }

    @Test
    void shouldReturnLessThanThreeElements() {
        when(artistsGenresService.getGenreArtists(anyString())).thenReturn(artistsList);
        assertTrue(topGenresService.getTopGenres(smallGenresMap).size() == 1);
    }

    @Test
    void shouldReturnThreeElementsEvenWhenTies() {
        when(artistsGenresService.getGenreArtists(anyString())).thenReturn(artistsList);
        assertTrue(topGenresService.getTopGenres(equalGenresMap).size() == 3);
    }
}