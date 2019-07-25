package app.service;

import app.model.TopGenreModel;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
class TopGenresServiceImplTest {

    private static TopGenreModel topGenreModel;
    private static List<String> artistsList = new ArrayList<>();

    private static Multimap<String, String> emptyArtGenMultimap = ArrayListMultimap.create();
    private static Multimap<String, String> equalArtGenMultimap = ArrayListMultimap.create();
    private static Multimap<String, String> smallArtGenMultimap = ArrayListMultimap.create();
    private static Multimap<String, String> typicalArtGenMultimap = ArrayListMultimap.create();

    private static Map<String, Integer> emptyGenresCount = new HashMap<>();
    private static Map<String, Integer> equalGenresCount = new HashMap<>();
    private static Map<String, Integer> smallGenresCount = new HashMap<>();
    private static Map<String, Integer> typicalGenresCount = new HashMap<>();

    @Mock
    @Resource
    static ArtistsGenresServiceImpl artistsGenresService;
    
    @InjectMocks
    @Autowired
    TopGenresServiceImpl topGenresService;


    @BeforeAll
    static void setup() {

        topGenreModel = new TopGenreModel(1, "Test Genre", new ArrayList<>());

        artistsList.add("unknown");

        topGenreModel.getArtists().add("Artist 1");
        topGenreModel.getArtists().add("Artist 2");
        topGenreModel.getArtists().add("Artist 3");

        smallArtGenMultimap.put("genre 1", "artist1");

        smallGenresCount.put("genre1", 2);


        typicalArtGenMultimap.put("genre1", "artist1");
        typicalArtGenMultimap.put("genre1", "artist2");
        typicalArtGenMultimap.put("genre1", "artist3");
        typicalArtGenMultimap.put("genre2", "artist4");
        typicalArtGenMultimap.put("genre3", "artist5");
        typicalArtGenMultimap.put("genre3", "artist1");
        typicalArtGenMultimap.put("genre4", "artist2");
        typicalArtGenMultimap.put("genre5", "artist6");
        typicalArtGenMultimap.put("genre5", "artist7");

        typicalGenresCount.put("genre1", 3);
        typicalGenresCount.put("genre2", 1);
        typicalGenresCount.put("genre3", 2);
        typicalGenresCount.put("genre4", 1);
        typicalGenresCount.put("genre5", 2);


        equalArtGenMultimap.put("genre1", "artist1");
        equalArtGenMultimap.put("genre1", "artist2");
        equalArtGenMultimap.put("genre2", "artist1");
        equalArtGenMultimap.put("genre2", "artist2");
        equalArtGenMultimap.put("genre3", "artist1");
        equalArtGenMultimap.put("genre3", "artist2");
        equalArtGenMultimap.put("genre4", "artist1");
        equalArtGenMultimap.put("genre4", "artist2");

        equalGenresCount.put("genre1", 2);
        equalGenresCount.put("genre2", 2);
        equalGenresCount.put("genre3", 2);
        equalGenresCount.put("genre4", 2);
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
        when(artistsGenresService.getArtistsGenresMultimap()).thenReturn(emptyArtGenMultimap);
        when(artistsGenresService.getGenresCount(any())).thenReturn(emptyGenresCount);

        assertTrue(topGenresService.getTopGenres().isEmpty());
    }

    @Test
    void shouldHaveOnlyOnePositionIfAllTies() {

        when(artistsGenresService.getArtistsGenresMultimap()).thenReturn(equalArtGenMultimap);
        when(artistsGenresService.getGenresCount(any())).thenReturn(equalGenresCount);
        when(artistsGenresService.getGenreArtists(any(), any())).thenReturn(artistsList);

        assertTrue(topGenresService.getTopGenres().stream().filter(g -> g.getPlace() > 1).count() == 0);
    }

    @Test
    void shouldReturnOnlyOneElement() {
        when(artistsGenresService.getArtistsGenresMultimap()).thenReturn(smallArtGenMultimap);
        when(artistsGenresService.getGenresCount(any())).thenReturn(smallGenresCount);
        when(artistsGenresService.getGenreArtists(any(), any())).thenReturn(artistsList);

        assertTrue(topGenresService.getTopGenres().size() == 1);
    }

    @Test
    void shouldAlwaysReturnMaxThreeElements() {
        when(artistsGenresService.getArtistsGenresMultimap()).thenReturn(typicalArtGenMultimap);
        when(artistsGenresService.getGenresCount(any())).thenReturn(typicalGenresCount);
        when(artistsGenresService.getGenreArtists(any(), any())).thenReturn(artistsList);

        assertTrue(topGenresService.getTopGenres().stream().filter(g -> g.getPlace() > 3).count() == 0);
    }
}