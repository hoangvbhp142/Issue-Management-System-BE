package com.example.issue_management_system.common.listener;

import com.example.issue_management_system.common.event.IssueStatusChangedEvent;
import com.example.issue_management_system.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueStatusChangedEventListener {

    private final NotificationServiceImpl notificationService;

    @Async
    @EventListener
    void handle(IssueStatusChangedEvent event) {
        notificationService.changeIssueStatus(event);
    }

}
