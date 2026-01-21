package com.example.issue_management_system.dto.response;

import com.example.issue_management_system.entity.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Map<IssueStatus, List<IssueDto>> listMap;
}
