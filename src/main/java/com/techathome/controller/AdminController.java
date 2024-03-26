package com.techathome.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin"); // admin.html sayfa şablonu
        // Model nesnesine veri ekleme örneği
        modelAndView.addObject("pageTitle", "Admin Panel");
        return modelAndView;
    }
}
