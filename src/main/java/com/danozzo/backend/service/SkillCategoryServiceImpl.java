package com.danozzo.backend.service;

import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.repository.SkillCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final SkillCategoryRepository skillCategoryRepository;

    @Override
    public SkillCategory createCategory(SkillCategory category) {
        return skillCategoryRepository.save(category);
    }

    @Override
    public List<SkillCategory> getAllCategories() {
        return skillCategoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        if (!skillCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with id " + id + " does not exist.");
        }
        skillCategoryRepository.deleteById(id);
    }
}
