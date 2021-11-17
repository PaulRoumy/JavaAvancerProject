package com.coding.javaav;


import com.coding.javaav.dao.ProductsDAO;
import com.coding.javaav.models.Products;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@Controller
public class ProductsController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String sql = "SELECT * FROM Products";


        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));


        return "pagination";
    }

    @RequestMapping(value ="/products/supp/{id}",method= RequestMethod.DELETE)
    public String supp(@PathVariable int id){
        productsService.suppProducts(id);
        return "index";
    }

    @RequestMapping(value = "/products/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Products> getById(@PathVariable long id, Model model){
        Products p = productsService.findById(id);
        if(p == null) {
            throw  new ResourceNotFoundException("User not found on :: " + id);
        }
        return ResponseEntity.ok().body(p);
    }

    @RequestMapping(value = "/products/modify/{id}", method = RequestMethod.PATCH)
    public String modify(@PathVariable int id, @RequestBody Products input){
        Products p = new Products();
        p.setCategoryId(input.getCategoryId());
        p.setRating(input.getRating());
        p.setname(input.getname());
        p.setType(input.getType());
        productsService.modifyProducts(id, p);

        return "index";
    }
}
