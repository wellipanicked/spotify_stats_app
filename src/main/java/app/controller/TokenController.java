package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@Controller
public class TokenController {

    private static String code;

    @RequestMapping("/logged/")
    public String getCode(@RequestParam(name = "code") String code, Model model) {
        model.addAttribute("code", code);
        this.code = code;
        System.out.println(getCode());
        return "logged";
    }

    public static String getCode() {
        return code;
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");

    return "logerror";
    }
}