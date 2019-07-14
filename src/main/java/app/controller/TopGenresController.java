package app.controller;

import app.service.TopGenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopGenresController {

    @Autowired
    TopGenresService topGenresService;

    private String empty = "You have no followed artists, or your artists don't have genres added - there's really nothing I can show you :(";
    private String notAll = "You need more followed artists - this list would've been bigger!";

    @RequestMapping("/genres")
    public String getTopGenres(Model model) {
        model.addAttribute("genres", topGenresService.getTopGenres());
        if (topGenresService.getTopGenres().isEmpty()) {
            model.addAttribute("empty", empty);
        } else if (topGenresService.getTopGenres().size() < 3) {
            model.addAttribute("notAll", notAll);
        }
        return "genres";
    }
}
