package com.danozzo.backend.controller;

import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.service.SkillCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-categories")
@RequiredArgsConstructor
public class SkillCategoryController {

    private final SkillCategoryService skillCategoryService;

    @PostMapping
    public SkillCategory createCategory(@RequestBody SkillCategory category){
        return skillCategoryService.createCategory(category);
    }

    @GetMapping
    public List<SkillCategory> getAllCategories(){
        return skillCategoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        skillCategoryService.deleteCategory(id);
    }

}
