package com.coding.javaav.dao;

import com.coding.javaav.models.Category;
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
        String sql = "SELECT * FROM users";
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


        List<Category> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class));

        return list;
    }

    public void addUser(String lastname, String firstname, String email, String phone){
        String sql = "INSERT INTO 'users' ('lastname', 'firstname', 'email', 'phone') VALUES ('"+ lastname+"','"+firstname+",";
    }
}