package com.danozzo.backend.service;

import com.danozzo.backend.model.Skill;
import com.danozzo.backend.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveSkill() {
        // Given
        Skill skill = getMockSkill();
        when(skillRepository.save(skill)).thenReturn(skill);

        // When
        Skill saved = skillService.saveSkill(skill);

        // Then
        assertEquals("Java", saved.getName());
        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    void shouldGetSkillsByUserId() {
        // Given
        when(skillRepository.findByUserId(1L)).thenReturn(List.of(getMockSkill()));

        // When
        List<Skill> result = skillService.getSkillsByUserId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(skillRepository).findByUserId(1L);
    }

    @Test
    void shouldDeleteSkillById() {
        // Given
        Long skillId = 1L;
        doNothing().when(skillRepository).deleteById(skillId);

        // When
        skillService.deleteSkill(skillId);

        // Then
        verify(skillRepository).deleteById(skillId);
    }

    private Skill getMockSkill() {
        return Skill.builder()
                .id(1L)
                .name("Java")
                .level("ADVANCED")
                .build();
    }

}