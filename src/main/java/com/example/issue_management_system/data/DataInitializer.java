package com.example.issue_management_system.data;

import com.example.issue_management_system.dto.request.RoleRequest;
import com.example.issue_management_system.service.impl.ProjectServiceImpl;
import com.example.issue_management_system.service.impl.RoleServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(
        name = "app.init_data",
        havingValue = "true"
)
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleServiceImpl roleService;
    private final ProjectServiceImpl projectService;

    private final List<RoleRequest> roleRequests = List.of(
            new RoleRequest("ROLE_USER"),
            new RoleRequest("ROLE_ADMIN")
    );

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (RoleRequest request: roleRequests) {
            roleService.create(request);
        }
    }
}
