package com.backendProject.blogger.services.impl;

import com.backendProject.blogger.entities.Category;
import com.backendProject.blogger.exceptions.ResourceNotFoundException;
import com.backendProject.blogger.payloads.CategoryDto;
import com.backendProject.blogger.repository.CategoryRepo;
import com.backendProject.blogger.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cate = this.modelMapper.map(categoryDto, Category.class);
        Category addedCate = this.categoryRepo.save(cate);
        return this.modelMapper.map(addedCate, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cate = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));

        cate.setCategoryTitle(categoryDto.getCategoryTitle());
        cate.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedcate = this.categoryRepo.save(cate);
        return this.modelMapper.map(updatedcate, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cate = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepo.delete(cate);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cate = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        return this.modelMapper.map(cate, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> catelist = this.categoryRepo.findAll();

        return catelist.stream().map((cate) -> this.modelMapper.map(cate, CategoryDto.class)).collect(Collectors.toList());
    }
}
