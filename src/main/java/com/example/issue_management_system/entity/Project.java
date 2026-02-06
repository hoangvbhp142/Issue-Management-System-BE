package com.example.issue_management_system.entity;

import com.example.issue_management_system.entity.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project extends BaseEntity {

    String code;
    String name;
    String description;

    @Enumerated(EnumType.STRING)
    ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User owner;
}
