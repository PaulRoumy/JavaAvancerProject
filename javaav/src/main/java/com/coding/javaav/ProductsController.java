package com.coding.javaav;


import com.coding.javaav.models.Category;
import com.coding.javaav.models.Products;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.util.List;

@Controller
public class ProductsController {
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/products")
    public String pagination(@RequestParam(value= "range") String range){
        System.out.println(range);
        String sql = "SELECT * FROM Products";


        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));


        return "pagination";
    }
}
