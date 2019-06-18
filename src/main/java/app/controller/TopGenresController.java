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

    @RequestMapping("/genres")
    public String getTopGenres(Model model) {
        model.addAttribute("genres", topGenresService.showTopGenres());
        return "genres";
    }
}
