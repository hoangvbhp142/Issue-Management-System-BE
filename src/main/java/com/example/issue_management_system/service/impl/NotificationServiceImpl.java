package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.common.event.IssueStatusChangedEvent;
import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.Notification;
import com.example.issue_management_system.entity.User;
import com.example.issue_management_system.entity.enums.NotificationStatus;
import com.example.issue_management_system.entity.enums.NotificationType;
import com.example.issue_management_system.common.event.IssueAssignedEvent;
import com.example.issue_management_system.repository.IssueRepository;
import com.example.issue_management_system.repository.NotificationRepository;
import com.example.issue_management_system.repository.UserRepository;
import com.example.issue_management_system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    @Override
    public void createIssueAssigned(IssueAssignedEvent event) {

        if (event.assigneeId().equals(event.assignedBy())) {
            return;
        }

        User receiver = userRepository.getReferenceById(event.assigneeId());

        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setPayload("""
                {
                    "issueId": %d,
                    "assigneeId": %d
                }
                """.formatted(event.issueId(), event.assigneeId()));
        notification.setType(NotificationType.ISSUE_ASSIGNED);
        notification.setStatus(NotificationStatus.UNREAD);

        notificationRepository.save(notification);
    }

    @Override
    public void changeIssueStatus(IssueStatusChangedEvent event) {
        Issue issue = issueRepository.getReferenceById(event.issueId());

        if ((issue.getAssignee().getId()).equals(issue.getReporter().getId())) {
            return;
        }

        User receiver = issue.getReporter();
        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setPayload("""
                {
                    "issueId": %d,
                    "changedBy": %d
                }
                """.formatted(event.issueId(), event.changedBy()));
        notification.setType(NotificationType.ISSUE_STATUS_CHANGED);
        notification.setStatus(NotificationStatus.UNREAD);

        notificationRepository.save(notification);
    }
}
