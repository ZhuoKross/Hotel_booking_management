package com.microservice.rooms.Controller;

import com.microservice.rooms.DTO.CategoryChildDTO;
import com.microservice.rooms.DTO.CategoryDTO;
import com.microservice.rooms.Service.CategoryService;
import com.microservice.rooms.Utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "categories", description = "Categories endpoint to manage the necessary operations to create, read and delete")
public class CategoryController {

    private CategoryService categoryService;
    public CategoryController (CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Operation(
            method = "GET",
            summary = "Method to fetch all categories stored in DB.",
            tags = {"get", "categories"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categories fetched successfully",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<Response<List<CategoryDTO>>> getAllCategories (){
        List<CategoryDTO> categoriesList = categoryService.getAllCategories();
        return ResponseEntity.ok(new Response<List<CategoryDTO>>("Categories fetched successfully", LocalDateTime.now(), categoriesList));
    }

    @Operation(
            method = "GET",
            summary = "Method to get ONE category",
            tags = {"get", "categories"},
            parameters = {
                    @Parameter(
                            name = "idCategory",
                            in = ParameterIn.PATH,
                            description = "The id of the category that will be fetched",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category fetched successfully.",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDTO>> getOneCategory (@PathVariable("id") Long idCategory){
        CategoryDTO category = categoryService.getOneCategory(idCategory);
        return ResponseEntity.ok(new Response<CategoryDTO>("Category fetched successfully", LocalDateTime.now(), category));
    }

    @Operation(
            method = "POST",
            summary = "Method to create a new category.",
            tags = {"categories", "post"},
            requestBody = @RequestBody(
                    description = "Request object to create a new category",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = CategoryChildDTO.class
                                    )
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category created successfully",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Response<CategoryDTO>> createOneCategory (@Valid @RequestBody CategoryChildDTO categoryDTO){
        CategoryDTO categoryCreated = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(new Response<CategoryDTO>("Category created successfully", LocalDateTime.now(), categoryCreated));
    }

    @Operation(
            method = "DELETE",
            summary = "Method to delete a category",
            parameters = {
                    @Parameter(
                            name = "idCategory",
                            description = "The id of the category that will be deleted",
                            required = true,
                            in = ParameterIn.PATH
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category deleted successfully",
                            content = {
                                    @Content(
                                            schema = @Schema(
                                                    implementation = Response.class
                                            )
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteCategory (@PathVariable("id") Long idCategory){
        categoryService.deleteCategory(idCategory);
        return ResponseEntity.ok(new Response<String>("Category deleted successfully", LocalDateTime.now(), "no data"));
    }

}
