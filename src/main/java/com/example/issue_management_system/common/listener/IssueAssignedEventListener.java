package com.example.issue_management_system.common.listener;

import com.example.issue_management_system.common.event.IssueAssignedEvent;
import com.example.issue_management_system.service.impl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IssueAssignedEventListener {

    private final NotificationServiceImpl notificationService;

    @Async
    @EventListener
    public void handle(IssueAssignedEvent event){
        log.info("Event received: {}", event);
        notificationService.createIssueAssigned(event);
    }

}
