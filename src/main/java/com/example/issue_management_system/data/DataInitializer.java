package com.example.issue_management_system.data;

import com.example.issue_management_system.dto.request.AdminUserRequest;
import com.example.issue_management_system.dto.request.ProjectRequest;
import com.example.issue_management_system.dto.request.RoleRequest;
import com.example.issue_management_system.service.impl.AdminUserServiceImpl;
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
    private final AdminUserServiceImpl adminUserService;

    private final List<RoleRequest> roleRequests = List.of(
            new RoleRequest("ROLE_USER"),
            new RoleRequest("ROLE_ADMIN")
    );

//    private final List<ProjectRequest> projectRequests = List.of(
//            new ProjectRequest("OmniMart", "Unified e-commerce platform supporting multiple storefronts"),
//            new ProjectRequest("TeamSync Pro", "Collaboration software with integrated project management and communication"),
//            new ProjectRequest("HealthTrack Plus", "Comprehensive wellness app with AI-powered health recommendations"),
//            new ProjectRequest("FinSecure", "Personal finance manager with investment tracking and security monitoring"),
//            new ProjectRequest("SmartHome Manager", "Centralized IoT platform for home automation and energy optimization")
//    );

    private final List<AdminUserRequest> adminUserRequests = List.of(
            new AdminUserRequest("admin1", "admin1@example.com", "1", "Nguyễn Văn A", List.of(2)),
            new AdminUserRequest("admin2", "admin2@example.com", "1", "Trần Thị B", List.of(2)),
            new AdminUserRequest("user1", "user1@example.com", "1", "Lê Văn C", List.of(1)),
            new AdminUserRequest("user2", "user2@example.com", "1", "Phạm Thị D", List.of(1)),
            new AdminUserRequest("user3", "user3@example.com", "1", "Hoàng Văn E", List.of(1))
    );

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (RoleRequest request: roleRequests) {
//            roleService.create(request);
//        }

//        for (ProjectRequest request: projectRequests) {
//            projectService.create(request);
//        }
        for (AdminUserRequest request: adminUserRequests) {
            adminUserService.create(request);
        }
    }
}
