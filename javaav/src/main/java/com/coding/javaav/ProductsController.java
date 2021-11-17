package com.coding.javaav;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {

    @RequestMapping("/products")
    public String pagination(@RequestParam(value= "range") String range){
        System.out.println(range);



        return "pagination";
    }
}
