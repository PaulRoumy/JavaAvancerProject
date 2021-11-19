package com.coding.javaav.dao;


import com.coding.javaav.models.Category;

import com.coding.javaav.models.Products;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public class ProductsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Products> listAll(){
        String sql = "SELECT * FROM Products";

        //List<Category> list = new ArrayList<>();

        /*try(Connection co =  DriverManager.getConnection("jdbc:mysql://localhost:3306/javaav", "root", "vincent")) {
            try(Statement ps = co.createStatement()) {
                try(ResultSet res = ps.executeQuery(sql)) {
                    while (res.next()) {
                        Category user = new Category();
                        user.setId(res.getLong("id"));
                        user.setPhone(res.getString("phone"));
                        user.setEmail(res.getString("email"));
                        user.setFirstname(res.getString("firstname"));
                        user.setLastname(res.getString("lastname"));
                        list.add(user);
                    }
                    return list;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       /*List<Category> list = jdbcTemplate.query(sql, new RowMapper<Category>() {

            @Override
            public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
                Category user = new Category();
                user.setId(rs.getLong("id"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                return user;
            }
        });*/


        List<Products> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Products.class));

        return list;
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
