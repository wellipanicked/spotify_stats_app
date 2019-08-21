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

    @RequestMapping("/genres")
    public String getTopGenres(Model model) {

        int howManyTops = 3;
        List<TopGenreModel> topGenres = topGenresService.getTopGenres(howManyTops);
        int actualHighestPlace = topGenresService.getActualHighestPlace(topGenres);

        model.addAttribute("howManyTops", howManyTops);
        model.addAttribute("actualHighestPlace", actualHighestPlace);
        model.addAttribute("topGenres", topGenres);

        if (topGenresService.getTopGenres(howManyTops).isEmpty()) {
            String empty = "You have no followed artists, or your artists don't have genres added - there's really nothing I can show you :(";
            model.addAttribute("empty", empty);
        } else if (actualHighestPlace < howManyTops) {
            String notAll = "You need to follow more artists - this list should've been bigger!";
            model.addAttribute("notAll", notAll);
        }
        return "genres";
    }
}
