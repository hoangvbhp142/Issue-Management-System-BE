# Issue Management System (Backend)

## Overview

**Issue Management System** là một **backend service** mô phỏng hệ thống quản lý công việc / lỗi (issue) trong các dự án phần mềm.
Hệ thống tập trung vào **thiết kế nghiệp vụ, phân quyền, authentication/authorization và kiến trúc backend**, không hướng tới UI.

### Chức năng chính:

* Quản lý Project
  * Quản lý thành viên trong Project với phân quyền
  * Quản lý Issue (tạo, assign, thay đổi trạng thái)
  * Lưu lịch sử thay đổi trạng thái Issue (audit)
  * Xác thực & phân quyền người dùng bằng JWT

---

## Tech Stack

* **Java 21**
  * **Spring Boot**
  * **Spring Security + JWT**
  * **Spring Data JPA (Hibernate)**
  * **RESTful API**
  * **MySQL**
  * **Maven**
  * **MinIO** (lưu trữ ảnh cho Issue)

---

## Authentication & Authorization

### Authentication

* Sử dụng **Spring Security + JWT**
  * Các API đăng ký / đăng nhập:

    * `POST /api/v1/auth/register`
    * `POST /api/v1/auth/login`
  * Sau khi đăng nhập thành công:

    * Server trả về **JWT**
    * Client gửi JWT qua header `Authorization: Bearer <token>`

### Authorization

* Không truyền `userId` từ client cho các nghiệp vụ chính
  * User hiện tại được lấy từ **SecurityContext**
  * Phân quyền dựa trên:

    * Role hệ thống (`ROLE_USER`, `ROLE_ADMIN`)
    * Role trong project (`OWNER`, `MEMBER`)

---

## Core Features

### Project Management

* Tạo project mới (user đăng nhập)
  * Cập nhật thông tin project
  * Archive project (soft delete)
  * Lấy danh sách project mà user đang tham gia

**Nghiệp vụ:**

* Người tạo project:

  * Tự động trở thành `OWNER`
  * Được thêm vào bảng `ProjectMember`
  * Project **không bị xoá cứng** khỏi database
  * Khi project bị archive:

    * Không cho phép tạo / cập nhật issue

* Create Project Flow
  * User đăng nhập → JWT
  * Gọi POST /projects
  * Backend lấy user từ SecurityContext
  * Tạo Project
  * Tạo ProjectMember với role OWNER
  * Trả ProjectDto

* Assign Issue Flow
  * User gọi PUT /issues/{id}/assign
  * Backend xác định user hiện tại
  * Nếu assign cho người khác → check OWNER
  * Nếu self-assign → cho phép
  * Update assignee
---

### Project Member & Role

Mỗi project quản lý thành viên thông qua entity trung gian `ProjectMember`.

**Role trong project:**

* `OWNER`
* `MEMBER`

**Quyền hạn:**

* `OWNER` có quyền:

  * Thêm / xoá thành viên
  * Thay đổi role thành viên
  * Không cho phép:

    * Xoá OWNER cuối cùng
    * OWNER tự hạ cấp chính mình

---

### Issue Management

* Member có thể tạo issue trong project
  * Issue bao gồm:

    * Title
    * Description
    * Status
    * Reporter
    * Assignee

**Assign Issue (nghiệp vụ):**

* `OWNER`:

  * Có thể assign issue cho bất kỳ member nào
  * `MEMBER`:

    * Chỉ được tự assign issue cho chính mình

---

### Issue Status & History (Audit)

**Trạng thái Issue:**

* `OPEN`
  * `IN_PROGRESS`
  * `RESOLVED`
  * `CLOSED`

**Nghiệp vụ:**

* Chỉ `Assignee` hoặc `OWNER` mới được thay đổi trạng thái
  * Trạng thái phải tuân theo **luồng hợp lệ**
  * Mỗi lần đổi trạng thái:

    * Hệ thống tự động ghi log vào bảng `IssueHistory`
    * Phục vụ audit và tracking tiến trình

---

### Comment & Image

* Issue có thể có:

  * Nhiều comment
  * Nhiều hình ảnh đính kèm
  * Ảnh được lưu trữ bằng **MinIO**
  * Backend chỉ lưu `objectKey`, không lưu file trực tiếp trong database

---

### Issue Dashboard & Summary

* Issue summary by:
  * Status
  * Type
  * Priority
* Tổng hợp bằng JPQL (GROUP BY, COUNT)
* Được sử dụng cho dashboard

---

## Database Design (Simplified)

**Main entities:**

* `User`
* `Role`
* `Project`
* `ProjectMember`
* `Issue`
* `IssueHistory`
* `Comment`
* `IssueImage`

**Quan hệ chính:**

* User – ProjectMember: One-to-Many
  * Project – ProjectMember: One-to-Many
  * Project – Issue: One-to-Many
  * Issue – IssueHistory: One-to-Many
  * Issue – Comment: One-to-Many
  * Issue – Image: One-to-Many

---

## API Structure (Example)

```text
AuthController
POST    /auth/register
POST    /auth/login

UserController
GET     /users/me
PUT     /users/me/profile
PUT     /users/me/password

AdminUserController
GET     /admin/users/{id}
GET     /admin/users
POST    /admin/users
PUT     /admin/users/{id}
DELETE  /admin/users/{id}

ProjectController
POST    /projects
GET     /projects
GET     /projects/{id}
POST    /projects/{id}/archive
GET     /projects/mine

ProjectMemberController
POST    /projects/{projectId}/members
DELETE  /projects/{projectId}/members/{userId}
PUT     /projects/{projectId}/members/{userId}/role

IssueController
POST    /issues
GET     /issues/{id}
GET     /projects/{projectId}/issues
PUT     /issues/{id}/assign
PUT     /issues/{id}/status
DELETE  /issues/{id}
GET     /issues/{projectId}/board

CommentController
POST    /comments
GET     /comments/issue/{issueId}

ImageController
POST    /issues/{issueId}/images
GET     /issues/{issueId}/images

IssueHistoryController
GET     /issue-histories/issue/{issueId}

SummaryController
GET     /summary/{projectId}/status
GET     /summary/{projectId}/type
GET     /summary/{projectId}/priority

```

---

## Project Goal

Mục tiêu của project:

* Thực hành thiết kế backend theo **real-world business logic**
  * Áp dụng **Spring Security + JWT**
  * Thiết kế phân quyền ở **Service layer**
  * Thể hiện tư duy:

    * Authorization theo ngữ cảnh (project-based)
    * Audit dữ liệu
    * Lifecycle của entity

---

## Future Improvements

* Permission chi tiết hơn (Issue-level permission)
  * Notification (email / websocket) khi:

    * Issue được assign
    * Issue đổi trạng thái
  * Pagination & filtering nâng cao
  * API rate limiting

---

## Author

**Backend Developer (Intern / Fresher)**
Tech stack: **Java – Spring Boot – Spring Security – JPA**

> Project được xây dựng bởi 1 thằng lười mãi không tìm được việc

