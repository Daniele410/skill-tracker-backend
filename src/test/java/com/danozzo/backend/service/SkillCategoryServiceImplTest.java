package com.danozzo.backend.service;

import com.danozzo.backend.model.SkillCategory;
import com.danozzo.backend.repository.SkillCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SkillCategoryServiceImplTest {

    @Mock
    private SkillCategoryRepository repository;

    @InjectMocks
    private SkillCategoryServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_shouldReturnSavedCategory() {
        // Given
        SkillCategory category = getMockCategory();
        when(repository.save(category)).thenReturn(category);

        // When
        SkillCategory result = service.createCategory(category);

        // Then
        assertNotNull(result);
        assertEquals("Backend", result.getName());
        verify(repository, times(1)).save(category);
    }

    @Test
    void getAllCategories_shouldReturnCategoryList() {
        // Given
        List<SkillCategory> categories = List.of(getMockCategory());
        when(repository.findAll()).thenReturn(categories);

        // When
        List<SkillCategory> result = service.getAllCategories();

        // Then
        assertEquals(1, result.size());
        assertEquals("Backend", result.get(0).getName());
        verify(repository).findAll();
    }

    @Test
    void deleteCategory_shouldCallRepository() {
        // Given
        Long id = 1L;
        SkillCategory category = getMockCategory();
        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(java.util.Optional.of(category));
        doNothing().when(repository).deleteById(id);

        // When
        service.deleteCategory(id);

        // Then
        verify(repository).deleteById(id);

    }

    private SkillCategory getMockCategory() {
        return SkillCategory.builder()
                .id(1L)
                .name("Backend")
                .build();
    }
}