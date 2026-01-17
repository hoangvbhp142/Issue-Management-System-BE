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
public class IssueImage extends BaseEntity {

    String objectKey;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    Issue issue;

}
