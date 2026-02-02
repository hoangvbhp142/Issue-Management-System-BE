package com.example.issue_management_system.entity;

import com.example.issue_management_system.entity.enums.RenderMode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment extends BaseEntity {

    String objectKey;
    String contentType;
    String fileName;
    Long size;

    @Enumerated(EnumType.STRING)
    RenderMode mode;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    Issue issue;

}
