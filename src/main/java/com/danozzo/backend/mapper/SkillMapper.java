package com.danozzo.backend.mapper;

import com.danozzo.backend.dto.SkillDTO;
import com.danozzo.backend.model.Skill;
import com.danozzo.backend.model.SkillCategory;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillDTO toDTO(Skill skill) {
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .level(skill.getLevel())
                .categoryId(skill.getCategory() != null ? skill.getCategory().getId() : null)
                .categoryName(skill.getCategory() != null ? skill.getCategory().getName() : null)
                .build();
    }

    public Skill toEntity(SkillDTO dto) {
        SkillCategory category = SkillCategory.builder()
                .id(dto.getCategoryId())
                .build();

        return Skill.builder()
                .id(dto.getId())
                .name(dto.getName())
                .level(dto.getLevel())
                .category(category)
                .build();
    }
}
