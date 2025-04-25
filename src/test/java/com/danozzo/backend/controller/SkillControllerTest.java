package com.danozzo.backend.controller;

import com.danozzo.backend.model.Skill;
import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.service.SkillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillService skillService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSkill_shouldReturnSkill() throws Exception {
        //Given
        Skill skill = getMockSkill();
        when(skillService.saveSkill(any(Skill.class))).thenReturn(skill);

        //When //Then
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getAllSkillsByUserId_shouldSkillsByUserId() throws Exception {
        //Given
        when(skillService.getSkillsByUserId(1L)).thenReturn(List.of(getMockSkill()));

        //When //Then
        mockMvc.perform(get("/api/skills/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Java"));
    }

    @Test
    void getAllSkillsByUserId_shouldReturnEmptyList() throws Exception {
        //Given
        when(skillService.getSkillsByUserId(1L)).thenReturn(List.of());

        //When //Then
        mockMvc.perform(get("/api/skills/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deleteSkill_shouldDeleteSkillById() throws Exception {
        // Given
        Long skillId = 1L;
        doNothing().when(skillService).deleteSkill(skillId);

        // When
        mockMvc.perform(delete("/api/skills/{id}", skillId))

                // Then
                .andExpect(status().isOk());
    }

    @Test
    void createSkill_withBlankFields_shouldReturn400() throws Exception {
        //Given
        Skill invalidSkill = Skill.builder()
                .id(null)
                .name("")    // blank
                .level("")   // blank
                .category(null) // null
                .build();

        //When //Then
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSkill)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Skill name is mandatory"))
                .andExpect(jsonPath("$.level").value("Skill level is mandatory"))
                .andExpect(jsonPath("$.category").value("Category is required"));

    }


    private Skill getMockSkill() {
        return Skill.builder()
                .id(1L)
                .name("Java")
                .level("ADVANCED")
                .category(SkillCategory.builder().id(1L).name("Backend").build())
                .build();
    }
}