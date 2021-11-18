package com.coding.javaav.dao;

import com.coding.javaav.models.Category;
import com.coding.javaav.models.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> listAll(){
        String sql = "SELECT * FROM Category";
        //List<Category> list = new ArrayList<>();

        List<Category> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class));

        return list;
    }

    public int addCategory(Category c){
        String sql = "INSERT INTO Category (name) VALUES (?)";
        return jdbcTemplate.update(sql, c.getName());
    }
    public int suppCategory(int id){
        String sql= "DELETE FROM Category WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    public int updateCategory(int id, Category c){
        String sql="UPDATE Category SET name=? WHERE id=?";
        return jdbcTemplate.update(sql, c.getName(),id);
    }
    public Category findById (long id){
        String sql="SELECT * FROM Category WHERE id=? ";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Category.class), id);
    }
}