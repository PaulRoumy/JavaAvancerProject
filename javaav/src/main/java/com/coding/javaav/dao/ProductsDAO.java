package com.coding.javaav.dao;


import com.coding.javaav.models.Category;

import com.coding.javaav.models.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
