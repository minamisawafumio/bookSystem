package com.itkoza.booksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
//bbbbbbbbbbbbbbbbbbbbb
@Controller
public class TopController {
    @GetMapping("/")
    public ModelAndView view() {
        ModelAndView model = new ModelAndView();
        model.addObject("id", "10000077");
    	model.setViewName("top");
    	
    	System.out.println("TopController view -------------");
 
//    	StringUtil.getInstance().addNum(null, 0);
    	
    	return model;
    }
}