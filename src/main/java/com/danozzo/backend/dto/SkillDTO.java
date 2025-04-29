package com.danozzo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {

    private Long id;

    @NotBlank(message = "Skill name is mandatory")
    private String name;

    @NotBlank(message = "Skill level is mandatory")
    private String level;

    @NotNull(message = "Skill category is mandatory")
    private Long categoryId;

    private String categoryName;
}
