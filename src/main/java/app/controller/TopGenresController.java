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
    private String notAll = "You need to follow more artists - this list should've been bigger!";

    @RequestMapping("/genres")
    public String getTopGenres(Model model) {

        int howManyTops = 3;
        List<TopGenreModel> topGenres = topGenresService.getTopGenres(howManyTops);
        int actualPlacesMax = topGenresService.getActualPlacesMax(topGenres);

        model.addAttribute("howManyTops", howManyTops);
        model.addAttribute("actualPlacesMax", actualPlacesMax);
        model.addAttribute("topGenres", topGenres);

        if (topGenresService.getTopGenres(howManyTops).isEmpty()) {
            model.addAttribute("empty", empty);
        } else if (actualPlacesMax < howManyTops) {
            model.addAttribute("notAll", notAll);
        }
        return "genres";
    }
}
