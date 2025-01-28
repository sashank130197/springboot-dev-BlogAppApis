 package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@RestControllerAdvice
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}
	
	
	// update
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		
		CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	
	
	// delete
	
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteteCategory(@PathVariable("catId")Integer catId){
		
	this.categoryService.deleteCategory(catId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully",true),HttpStatus.OK);
	}
	
	
	// getAll
	
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto> >getCategories(){
		
	List<CategoryDto> categoryDto=this.categoryService.getAllCategories();
	return ResponseEntity.ok(categoryDto);
	}
	
	//getById
	
	
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId")Integer catId){
		
	CategoryDto categoryDto=this.categoryService.getCategory(catId);
	return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}

}
