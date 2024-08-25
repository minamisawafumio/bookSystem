package com.itkoza.booksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    @GetMapping("/nextpage")
    public ModelAndView viewNextPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("nextpage");
        return model;
    }
    
    @GetMapping("/gamen1")
    public ModelAndView viewGamen1() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Gamen1");
        return model;
    }

    @GetMapping("/gamen2")
    public ModelAndView viewGamen2() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Gamen2");
        return model;
    }
     
    @GetMapping("/gamen3")
    public ModelAndView viewGamen3() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Gamen3");
        return model;
    }
    
    @GetMapping("/gamen4")
    public ModelAndView viewGamen4() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Gamen4");
        return model;
    }

}