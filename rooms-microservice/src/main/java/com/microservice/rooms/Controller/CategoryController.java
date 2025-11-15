package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.CategoryDTO;
import com.microservice.rooms.Service.CategoryService;
import com.microservice.rooms.Utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;
    public CategoryController (CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<CategoryDTO>>> getAllCategories (){
        List<CategoryDTO> categoriesList = categoryService.getAllCategories();
        return ResponseEntity.ok(new Response<List<CategoryDTO>>("Categories fetched successfully", LocalDateTime.now(), categoriesList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> getOneCategory (@PathVariable("id") Long idCategory){
        CategoryDTO category = categoryService.getOneCategory(idCategory);
        return ResponseEntity.ok(new Response<CategoryDTO>("Category fetched successfully", LocalDateTime.now(), category));
    }

    @PostMapping("/create")
    public ResponseEntity<Response<CategoryDTO>> createOneCategory (@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO categoryCreated = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(new Response<CategoryDTO>("Category created successfully", LocalDateTime.now(), categoryCreated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteCategory (@PathVariable("id") Long idCategory){
        categoryService.deleteCategory(idCategory);
        return ResponseEntity.ok(new Response<String>("Category deleted successfully", LocalDateTime.now(), "no data"));
    }

}
