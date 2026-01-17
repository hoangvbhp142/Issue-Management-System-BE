# Issue Management System (Backend)

## Overview

Issue Management System là một **backend service** mô phỏng hệ thống quản lý công việc / lỗi (issue) trong các dự án phần mềm.

Hệ thống cho phép:

* Quản lý Project
* Quản lý thành viên trong Project với phân quyền
* Quản lý Issue (tạo, assign, thay đổi trạng thái)
* Lưu lịch sử thay đổi trạng thái Issue (audit)

Dự án tập trung vào **thiết kế nghiệp vụ, phân quyền và kiến trúc backend**, không hướng tới UI.

---

## Tech Stack

* **Java 21**
* **Spring Boot**
* **Spring Data JPA (Hibernate)**
* **RESTful API**
* **MySQL**
* **Maven**

---

## Core Features

### Project Management

* Tạo project mới
* Cập nhật thông tin project
* Archive project (soft delete)
* Lấy danh sách project mà user tham gia

**Lưu ý:**

* Project không bị xoá cứng khỏi database
* Khi project bị archive, các thao tác tạo / cập nhật issue sẽ bị chặn

---

### Project Member & Role

Mỗi project có danh sách thành viên riêng thông qua entity `ProjectMember`.

**Role hiện tại:**

* `OWNER`
* `MEMBER`

**Nghiệp vụ chính:**

* OWNER có quyền:

    * Thêm thành viên
    * Xoá thành viên
    * Thay đổi role
* Không cho phép:

    * Xoá OWNER cuối cùng
    * OWNER tự hạ cấp chính mình

---

### Issue Management

* Member có thể tạo issue trong project
* Issue có các thông tin:

    * Title
    * Description
    * Status
    * Reporter
    * Assignee

**Assign issue:**

* OWNER có thể assign issue cho người khác
* MEMBER chỉ có thể tự assign cho chính mình

---

### Issue Status & History

* Issue có các trạng thái: `OPEN`, `IN_PROGRESS`, `DONE`, `REOPEN`
* Chỉ Assignee hoặc OWNER mới được thay đổi trạng thái
* Khi trạng thái issue thay đổi:

    * Hệ thống tự động lưu lịch sử vào `IssueHistory`
    * Phục vụ audit và theo dõi tiến trình

---

### Authorization Design

* Không sử dụng Spring Security (ở mức demo)
* Quyền được kiểm tra tại **Service layer**
* Controller chỉ làm nhiệm vụ nhận request và trả response
* Phân quyền dựa trên:

    * Membership trong project
    * Role của user trong project

---

## Database Design (Simplified)

**Main entities:**

* `User`
* `Project`
* `ProjectMember`
* `Issue`
* `IssueHistory`

Quan hệ:

* Project – ProjectMember: One-to-Many
* User – ProjectMember: One-to-Many
* Project – Issue: One-to-Many
* Issue – IssueHistory: One-to-Many

---

## API Structure (Example)

```text
GET     /users/{id}
GET     /users
POST    /users
PUT     /users/{id}
DELETE  /users/{id}


GET     /issues/{id}
GET     /issues
POST    /issues
PUT     /issues/{id}/assign
PUT     /issues/{id}/status
DELETE    /issues/{id}

POST    /comments
GET     /comments/issue/{issueId}

GET     /issue-histories/issue/{issueId}

POST    /api/projects
GET     /api/projects
GET     /api/projects/{id}
POST    /api/projects/{id}/archive

POST    /api/projects/{projectId}/members
DELETE  /api/projects/{projectId}/members/{userId}
PUT     /api/projects/{projectId}/members/{userId}/role

POST    /api/issues
PUT     /api/issues/{id}/assign
PUT     /api/issues/{id}/status
```

---

## Project Goal

Mục tiêu của project:

* Thực hành thiết kế backend theo **real-world business logic**
* Tách rõ responsibility giữa Controller – Service – Repository
* Thể hiện tư duy phân quyền, audit và lifecycle dữ liệu

---

## Future Improvements

* Tích hợp Spring Security + JWT
* Permission chi tiết hơn (Issue-level permission)
* Comment cho Issue
* Notification khi issue được assign / đổi trạng thái

---

## Author

* **Backend Developer (Fresher / Junior)**
* Tech stack: Java – Spring Boot – JPA
* **Project được làm bởi 1 thằng vừa mới ra trường và đang thất nghiệp, 
mong tìm được 1 công việc đúng chuyên môn**
---


trien khai project, project member