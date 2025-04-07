package com.danozzo.backend.controller;

import com.danozzo.backend.model.Skill;
import com.danozzo.backend.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.saveSkill(skill);
    }

    @GetMapping("/user/{userId}")
    public List<Skill> getAllSkillsByUserId(@PathVariable Long userId) {
        return skillService.getSkillsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}
