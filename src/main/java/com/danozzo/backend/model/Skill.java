package com.danozzo.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is mandatory")
    private String name;

    @NotBlank(message = "Skill level is mandatory")
    private String level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
