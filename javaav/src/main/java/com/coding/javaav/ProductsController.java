package com.coding.javaav;


import com.coding.javaav.dao.ProductsDAO;
import com.coding.javaav.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductsController {
    @Autowired
    private ProductsDAO productsService;


    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String add(@RequestBody Products input){
        Products p = new Products();
        p.setCategoryId(input.getCategoryId());
        p.setRating(input.getRating());
        p.setname(input.getname());
        p.setType(input.getType());
        productsService.addProducts(p);
        return "index";
    }
    @RequestMapping("/products")
    public String pagination(@RequestParam(value= "range") String range){
        System.out.println(range);



        return "pagination";
    }

    @RequestMapping(value ="/products/supp/{id}",method= RequestMethod.DELETE)
    public String supp(@PathVariable int id){
        productsService.suppProducts(id);
        return "index";
    }
}
