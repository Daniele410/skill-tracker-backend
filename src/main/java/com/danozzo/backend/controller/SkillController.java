package com.danozzo.backend.controller;

import com.danozzo.backend.dto.SkillDTO;
import com.danozzo.backend.mapper.SkillMapper;
import com.danozzo.backend.model.Skill;
import com.danozzo.backend.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @PostMapping
    public SkillDTO createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        Skill skill= skillMapper.toEntity(skillDTO);
        Skill savedSkill = skillService.saveSkill(skill);
        return skillMapper.toDTO(savedSkill);
    }

    @GetMapping("/user/{userId}")
    public List<SkillDTO> getSkillsByUserId(@PathVariable Long userId) {
        List<Skill> skills = skillService.getSkillsByUserId(userId);
        return skills.stream()
                .map(skillMapper::toDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}
