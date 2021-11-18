package com.coding.javaav;

import com.coding.javaav.dao.CategoryDAO;
import com.coding.javaav.models.Category;
import com.coding.javaav.models.Products;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CategoryController {

    @Autowired
    private CategoryDAO categoryService;

    @RequestMapping("/category")
    public String index(Model model){
        model.addAttribute("listCategory", categoryService.listAll());
        return "index";
    }

    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    public String add(@RequestBody Category input){
        Category c = new Category();
        c.setName(input.getName());
        categoryService.addCategory(c);
        return "index";
    }
    @RequestMapping(value ="/category/supp/{id}",method= RequestMethod.DELETE)
    public String supp(@PathVariable int id){
        categoryService.suppCategory(id);
        return "index";
    }
    @RequestMapping(value = "/category/update/{id}", method = RequestMethod.PATCH)
    public String update(@PathVariable int id, @RequestBody Category input){
        Category c = new Category();
        c.setName(input.getName());
        categoryService.updateCategory(id, c);
        return "index";
    }
    @RequestMapping(value = "/category/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getById(@PathVariable long id, Model model){
        Category c = categoryService.findById(id);
        if(c == null) {
            throw  new ResourceNotFoundException("Category not found on :: " + id);
        }
        return ResponseEntity.ok().body(c);
    }
}
