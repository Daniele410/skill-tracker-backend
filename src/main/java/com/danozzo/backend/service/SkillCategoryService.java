package com.danozzo.backend.service;

import com.danozzo.backend.model.SkillCategory;

import java.util.List;

public interface SkillCategoryService {

    SkillCategory createCategory(SkillCategory category);

    List<SkillCategory> getAllCategories();

    void deleteCategory(Long id);

    SkillCategory findCategoryById(Long id);
}
