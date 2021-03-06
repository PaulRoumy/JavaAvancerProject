package com.coding.javaav.dao;


import com.coding.javaav.models.Category;

import com.coding.javaav.models.Products;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;

@Service
@Repository
public class ProductsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ResponseEntity<List<Products>> listAll(){
        String sql = "SELECT * FROM Products";
        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));

       return ResponseEntity.ok().body(list);
    }

    public int addProducts(Products p){
        String sql = "INSERT INTO Products (rating, name, type, categoryId) VALUES (?,?,?,?)";
        return jdbcTemplate.update(sql, p.getRating(), p.getname(), p.getType(),p.getCategoryId());
    }

    public int suppProducts(int id){
        String sql= "DELETE FROM Products WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    public Products findById(int id) {
        String sql = "SELECT * FROM Products WHERE id=? ;";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Products.class), id);
    }
    public int modifyProducts(int id, Products p){
        String sql="UPDATE Products SET type=?, name=?, rating=?, categoryId=? WHERE id=?";
        return jdbcTemplate.update(sql, p.getType(), p.getname(), p.getRating(),p.getCategoryId(),id);
    }

    public ResponseEntity<List<Products>> pagination(String range){
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
    public List<Products> recherche(String name,String type,String rating) {
        String sql = "";

        ArrayList value = new ArrayList();
        ArrayList condition = new ArrayList();

        if (name != null) {
            condition.add("name = ?");
            value.add(name);
        }
        if (type != null) {
            condition.add("type = ?");
            value.add(type);
        }
        if (rating != null) {
            condition.add("rating = ?;");
            value.add(rating);
        }


        List<Products> list = new ArrayList<>();
        int valueSize = value.size();
        switch (valueSize) {
            case 1:
                sql = "SELECT * FROM Products WHERE " + condition.get(0);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), value.get(0));
                break;
            case 2:
                sql = "SELECT * FROM Products WHERE " + condition.get(0) + " AND " + condition.get(1);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), value.get(0), value.get(1));
                break;
            case 3:
                sql = "SELECT * FROM Products WHERE " + condition.get(0) + " AND " + condition.get(1) + " AND " + condition.get(2);
                list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), value.get(0), value.get(1), value.get(2));
                break;
        }


        return list;
    }
    public ResponseEntity<List<Products>> tri(String value1,String value2) {
        String sql = "SELECT * FROM Products ORDER BY ? ASC, ? DESC";
        if ((value1.equals("rating") && value2.equals("name")) || value1.equals("name") && value2.equals("rating")) {
            List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class), value1, value2);
            return ResponseEntity.ok().body(list);
        } else {
            throw new ResourceNotFoundException("value is not right");

        }


    }
}
