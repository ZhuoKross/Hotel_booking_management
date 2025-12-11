package com.microservice.rooms.Service;

import com.microservice.rooms.DTO.CategoryChildDTO;
import com.microservice.rooms.DTO.CategoryDTO;
import com.microservice.rooms.Entity.Category;
import com.microservice.rooms.Repository.CategoryRepository;
import com.microservice.rooms.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories (){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map((category)-> {
            return CategoryDTO.builder()
                    .id(category.Id)
                    .name(category.name)
                    .build();
        }).toList();
    }

    public CategoryDTO getOneCategory (Long idCategory){
        if(idCategory == null){
            throw new IllegalArgumentException();
        }

        Category categoryFound = categoryRepository.findById(idCategory).orElseThrow(() -> new CategoryNotFoundException("The categories does not exists"));
        return CategoryDTO.builder()
                .id(categoryFound.Id)
                .name(categoryFound.name)
                .build();
    }


    public CategoryDTO createCategory (CategoryChildDTO categoryDTO){
        Category categoryCreated = categoryRepository.save(Category.builder()
                        .name(categoryDTO.name())
                        .build());

        return CategoryDTO.builder()
                .id(categoryCreated.Id)
                .name(categoryCreated.name)
                .build();
    }


    public void deleteCategory (Long idCategory){
        if(idCategory == null){
            throw new IllegalArgumentException();
        }

        categoryRepository.deleteById(idCategory);
    }
}
