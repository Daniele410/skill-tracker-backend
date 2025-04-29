package com.danozzo.backend.service;

import com.danozzo.backend.model.Skill;
import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillCategoryService skillCategoryService;

    @Override
    public Skill saveSkill(Skill skill) {
        SkillCategory category = skillCategoryService.findCategoryById(skill.getCategory().getId());
        skill.setCategory(category);
        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> getSkillsByUserId(Long userId) {
        return skillRepository.findByUserId(userId);
    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
