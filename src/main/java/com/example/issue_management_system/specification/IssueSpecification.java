package com.example.issue_management_system.specification;

import com.example.issue_management_system.entity.Issue;
import com.example.issue_management_system.entity.enums.IssuePriority;
import com.example.issue_management_system.entity.enums.IssueStatus;
import com.example.issue_management_system.entity.enums.IssueType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class IssueSpecification {

    public static Specification<Issue> project(Integer projectId) {

        return (root, query, criteriaBuilder) -> {
            if (projectId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("project").get("id"), projectId);
        };
    }

    public static Specification<Issue> keywordFilter(String keyword) {
        return (root, query, criteriaBuilder) -> {

            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            "%" + keyword.toLowerCase() + "%"
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            "%" + keyword.toLowerCase() + "%"
                    )
            );
        };
    }

    public static Specification<Issue> statusesFilter(Set<IssueStatus> statuses) {

        return (root, query, criteriaBuilder) -> {
            if (statuses == null || statuses.isEmpty())
                return criteriaBuilder.conjunction();

            return root.get("status").in(statuses);
        };
    }

    public static Specification<Issue> typesFilter(Set<IssueType> types) {

        return (root, query, criteriaBuilder) -> {
            if (types == null || types.isEmpty())
                return criteriaBuilder.conjunction();

            return root.get("type").in(types);
        };
    }

    public static Specification<Issue> assigneeFilter(Integer assigneeId) {

        return (root, query, criteriaBuilder) -> {
            if (assigneeId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
        };
    }

    public static Specification<Issue> prioritiesFilter(Set<IssuePriority> priorities) {

        return (root, query, criteriaBuilder) -> {
            if (priorities == null || priorities.isEmpty())
                return criteriaBuilder.conjunction();

            return root.get("priority").in(priorities);
        };
    }
}
