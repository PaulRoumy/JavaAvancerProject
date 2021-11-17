package com.coding.javaav;

import com.coding.javaav.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

    @Autowired
    private UserDAO userService;

    @RequestMapping("/users")
    public String index(Model model){
        model.addAttribute("listUsers", userService.listAll());
        return "index";
    }

    @RequestMapping("/users/add")
    public String add(Model model){
        model.addAttribute("listUsers", userService.listAll());
        return "index";
    }

}
