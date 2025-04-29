package com.danozzo.backend.controller;

import com.danozzo.backend.dto.SkillDTO;
import com.danozzo.backend.mapper.SkillMapper;
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

    @MockitoBean
    private SkillMapper skillMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSkill_shouldReturnSkill() throws Exception {
        // Given
        SkillDTO skillDTO = getMockSkillDTO();
        when(skillService.saveSkill(any(Skill.class))).thenReturn(getMockSkill());
        when(skillMapper.toEntity(any(SkillDTO.class))).thenReturn(getMockSkill());
        when(skillMapper.toDTO(any(Skill.class))).thenReturn(getMockSkillDTO());

        // When // Then
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Backend"));
    }

    @Test
    void getAllSkillsByUserId_shouldSkillsByUserId() throws Exception {
        // Given
        when(skillService.getSkillsByUserId(1L)).thenReturn(List.of());

        // When // Then
        mockMvc.perform(get("/api/skills/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllSkillsByUserId_shouldReturnEmptyList() throws Exception {
        //Given
        when(skillService.getSkillsByUserId(1L)).thenReturn(List.of());
        when(skillMapper.toEntity(any(SkillDTO.class))).thenReturn(getMockSkill());
        when(skillMapper.toDTO(any(Skill.class))).thenReturn(getMockSkillDTO());

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
        // Given
        SkillDTO invalidSkillDTO = SkillDTO.builder()
                .id(null)
                .name("")    // blank
                .level("")   // blank
                .categoryId(null) // null
                .build();

        // When // Then
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSkillDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Skill name is mandatory"))
                .andExpect(jsonPath("$.level").value("Skill level is mandatory"))
                .andExpect(jsonPath("$.categoryId").value("Skill category is mandatory"));
    }

    @Test
    void createSkill_withInvalidCategory_shouldReturn404() throws Exception {
        // Given
        SkillDTO skillDTO = getMockSkillDTO();
        Skill skill = getMockSkill();
        skillDTO.setCategoryId(999L);

        when(skillMapper.toEntity(any(SkillDTO.class))).thenReturn(skill);
        when(skillService.saveSkill(any(Skill.class)))
                .thenThrow(new IllegalArgumentException("Category with id 999 does not exist."));

        // When // Then
        mockMvc.perform(post("/api/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skillDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Category with id 999 does not exist."));
    }

    private Skill getMockSkill() {
        return Skill.builder()
                .id(1L)
                .name("Java")
                .level("ADVANCED")
                .category(SkillCategory.builder()
                        .id(1L)
                        .name("Backend")
                        .build())
                .build();
    }

    private SkillDTO getMockSkillDTO() {
        return SkillDTO.builder()
                .id(1L)
                .name("Java")
                .level("ADVANCED")
                .categoryId(1L)
                .categoryName("Backend")
                .build();
    }
}