package com.techathome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); // index.html sayfa şablonu
        // Model nesnesine veri ekleme örneği
        modelAndView.addObject("pageTitle", "Home Page");
        return modelAndView;
    }
}
