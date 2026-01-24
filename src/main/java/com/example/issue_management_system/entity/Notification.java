package com.example.issue_management_system.entity;

import com.example.issue_management_system.entity.enums.NotificationStatus;
import com.example.issue_management_system.entity.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;

    String payload;

    @Enumerated(EnumType.STRING)
    NotificationType type;

    @Enumerated(EnumType.STRING)
    NotificationStatus status;

}
