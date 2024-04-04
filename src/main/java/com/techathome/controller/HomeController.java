package com.techathome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public ModelAndView home(
    		@RequestParam(required = false) Long categoryId, 
    		@RequestParam(required = false) Long subcategoryId,
    		@RequestParam(required = false) String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // index.html sayfa şablonu
        // Model nesnesine veri ekleme örneği
        modelAndView.addObject("pageTitle", "Home Page");
        modelAndView.addObject("categoryId", categoryId);
        modelAndView.addObject("subcategoryId", subcategoryId);
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
