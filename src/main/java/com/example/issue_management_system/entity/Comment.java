package com.example.issue_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity{
    String content;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    Issue issue;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
