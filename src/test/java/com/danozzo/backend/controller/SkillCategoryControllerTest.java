package com.danozzo.backend.controller;

import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.service.SkillCategoryService;
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


@WebMvcTest(SkillCategoryController.class)
class SkillCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillCategoryService skillCategoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory_shouldReturnCreatedCategory() throws Exception {
        // Given
        SkillCategory category = getMockCategory();
        when(skillCategoryService.createCategory(any(SkillCategory.class))).thenReturn(category);

        // When // Then
        mockMvc.perform(post("/api/skill-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Backend"));
    }

    @Test
    void getAllCategories_shouldReturnCategoriesList() throws Exception {
        // Given
        List<SkillCategory> categories = List.of(getMockCategory());
        when(skillCategoryService.getAllCategories()).thenReturn(categories);

        // When // Then
        mockMvc.perform(get("/api/skill-categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Backend"));
    }

    @Test
    void deleteCategory_shouldReturnOk() throws Exception {
        // Given
        Long categoryId = 1L;
        doNothing().when(skillCategoryService).deleteCategory(categoryId);

        // When // Then
        mockMvc.perform(delete("/api/skill-categories/{id}", categoryId))
                .andExpect(status().isOk());
    }

    private SkillCategory getMockCategory() {
        return SkillCategory.builder()
                .id(1L)
                .name("Backend")
                .build();
    }

}