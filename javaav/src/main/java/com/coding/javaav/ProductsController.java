package com.coding.javaav;



import com.coding.javaav.models.Products;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coding.javaav.dao.ProductsDAO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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
    public ResponseEntity<List<Products>> pagination(@RequestParam(value="range") String range){




        String separateur = "-";
        String[] value = range.split(separateur);
        String sql = "SELECT * FROM Products LIMIT ? OFFSET ?";
        int limit = Integer.parseInt(value[1]);
        int offset = Integer.parseInt(value[0]);

        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), limit, offset);

        int test = limit+offset;
        String retour = String.valueOf(limit)+"/"+String.valueOf(test);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Range:", retour);
        headers.add("Accept-Range:", "product: "+limit);

        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }


    @RequestMapping(value ="/products/supp/{id}",method= RequestMethod.DELETE)
    public String supp(@PathVariable int id){
        productsService.suppProducts(id);
        return "index";
    }

    @RequestMapping(value ="/products/search")
    public List<Products> search(
            @RequestParam(value="name",required = false) String name ,
            @RequestParam(value="type",required = false) String type,
            @RequestParam(value="rating",required = false) String rating
            ){
        String sql = "";

        ArrayList value = new ArrayList() ;
        ArrayList condition = new ArrayList() ;

        if(name != null){
            condition.add("name = ?");
            value.add(name);
        }
        if (type != null){
            condition.add("type = ?");
            value.add(type);
        }
        if (rating != null){
            condition.add("rating = ?;");
            value.add(rating);
        }



        List<Products> list = new ArrayList<>();
        int valueSize = value.size();
        switch (valueSize){
            case 1:
                sql = "SELECT * FROM Products WHERE "+condition.get(0);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), value.get(0));
                break;
            case 2:
                sql = "SELECT * FROM Products WHERE "+condition.get(0)+" AND "+condition.get(1);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class),value.get(0),value.get(1));
                break;
            case 3:
                sql = "SELECT * FROM Products WHERE "+condition.get(0)+" AND "+condition.get(1)+" AND "+condition.get(2);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class),value.get(0),value.get(1),value.get(2));
                break;
        }




        return list;
    }

}
