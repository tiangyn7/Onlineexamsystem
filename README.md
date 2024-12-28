# 在线考试管理系统

## 项目概述

本项目是一个基于 Java 的在线考试管理系统。系统通过 JSP 和 Servlet 技术实现，结合 MySQL 数据库，提供考试的管理、问题的管理以及考试详情的展示功能。适用于开发和学习在线教育平台的核心功能。

---

## 功能特点

- **考试管理**:
  - 创建、修改和删除考试。
  - 查看考试详情，包括考试名称、时间、描述等。

- **问题管理**:
  - 添加、修改和删除问题。
  - 将问题与特定考试关联。

- **考试详情展示**:
  - 列出考试的所有问题。
  - 支持按考试 ID 查询问题。

---

## 项目结构

```plaintext
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/    # 包含 Servlet 文件
│   │   │   │   ├── ExamServlet.java
│   │   │   │   ├── QuestionServlet.java
│   │   │   ├── service/       # 服务接口
│   │   │   │   ├── ExamService.java
│   │   │   │   ├── QuestionService.java
│   │   │   ├── service/impl/  # 服务实现
│   │   │   │   ├── ExamServiceImpl.java
│   │   │   │   ├── QuestionServiceImpl.java
│   │   │   ├── model/         # 数据模型
│   │   │   │   ├── Exam.java
│   │   │   │   ├── Question.java
│   │   ├── webapp/            # JSP 页面
│   │   │   ├── listExams.jsp
│   │   │   ├── manageExam.jsp
│   │   │   ├── manageQuestions.jsp
├── pom.xml                    # Maven 配置文件
