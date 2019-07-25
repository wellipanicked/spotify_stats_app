package app.controller;

import app.model.TopGenreModel;
import app.service.TopGenresServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TopGenresController {

    @Autowired
    TopGenresServiceImpl topGenresService;

    private String empty = "You have no followed artists, or your artists don't have genres added - there's really nothing I can show you :(";
    private String notAll = "You need to follow more artists - this list could've been bigger!";

    @RequestMapping("/genres")
    public String getTopGenres(Model model) {

        List<TopGenreModel> topGenres = topGenresService.getTopGenres();
        model.addAttribute("topGenre1", topGenresService.placeList(topGenres, 1));
        model.addAttribute("topGenre2", topGenresService.placeList(topGenres, 2));
        model.addAttribute("topGenre3", topGenresService.placeList(topGenres, 3));
        if (topGenresService.getTopGenres().isEmpty()) {
            model.addAttribute("empty", empty);
        } else if (topGenresService.placeList(topGenres, 2).isEmpty() || topGenresService.placeList(topGenres, 3).isEmpty()) {
            model.addAttribute("notAll", notAll);
        }
        return "genres";
    }
}
