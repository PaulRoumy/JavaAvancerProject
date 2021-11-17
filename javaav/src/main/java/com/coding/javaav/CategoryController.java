package com.coding.javaav;

import com.coding.javaav.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {

    @Autowired
    private CategoryDAO userService;

    @RequestMapping("/category")
    public String index(Model model){
        model.addAttribute("listCategory", userService.listAll());
        return "index";
    }

    @RequestMapping("/category/add")
    public String add(Model model){
        model.addAttribute("listCategory", userService.listAll());
        return "index";
    }

}
