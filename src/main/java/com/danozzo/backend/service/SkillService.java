package com.danozzo.backend.service;

import com.danozzo.backend.model.Skill;

import java.util.List;

public interface SkillService {
    Skill save(Skill skill);

    List<Skill> getSkillsByUserId(Long userId);

    void deleteSkill(Long id);
}
