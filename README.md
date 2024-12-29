# 在线考试管理系统

## 项目概述

本项目是一个基于 Java 的在线考试管理系统。系统通过 JSP 和 Servlet 技术实现，结合 MySQL 数据库，提供考试的管理、问题的管理以及考试详情的展示功能。适用于开发和学习在线教育平台的核心功能。

---

## 功能特点

- **考试管理**：
  - 创建、修改和删除考试。
  - 查看考试详情，包括考试名称、时间、描述等。
  
- **问题管理**：
  - 添加、修改和删除问题。 
  - 将问题与特定考试关联。
  
- **考试详情展示**：
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
```

---

## 数据库结构

### 数据表

#### `exams` 表

存储考试信息。

```plaintext
字段名       描述           
id           考试 ID        
name         考试名称       
start_time   考试开始时间   
end_time     考试结束时间   
description  考试描述       
duration     考试时长       
```

#### `questions` 表

存储问题信息。

```plaintext
字段名         描述           
id             问题 ID        
question_text  问题内容       
correct_answer 正确答案       
score          分数           
type           类型           
difficulty     难度           
```

#### `exam_questions` 表

用于关联考试和问题。

```plaintext
字段名       描述           
id           ID             
exam_id      考试 ID        
question_id  问题 ID        
```

---

## 部署指南

### 前置要求
- **JDK**：JDK 8 或更高版本。
- **Tomcat**：Apache Tomcat 9.0 或更高版本。
- **数据库**：MySQL 8.0 或更高版本。
- **构建工具**：Maven 3.6 或更高版本。


### 安装步骤

#### 克隆项目：
git clone https://github.com/your-repo/online-exam-system.git
cd online-exam-system

#### 创建数据库并导入 SQL 脚本：

```sql
CREATE DATABASE online_exam;
USE online_exam;

-- 创建表
CREATE TABLE exams (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    description TEXT,
    duration INT
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    correct_answer VARCHAR(255),
    score INT,
    type VARCHAR(50),
    difficulty VARCHAR(50)
);

CREATE TABLE exam_questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    exam_id INT NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);
```

#### 配置数据库连接：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_exam
spring.datasource.username=root
spring.datasource.password=yourpassword
```

#### 构建项目：

```bash
mvn clean install
```

#### 部署到 Tomcat：
将生成的 WAR 文件复制到 Tomcat 的 webapps 目录。

#### 启动 Tomcat：
启动 Tomcat 后，在浏览器访问：http://localhost:8080/demo


---

## 使用说明
- **进入系统**：打开浏览器，访问 http://localhost:8080/demo。
- **管理考试**：查看和编辑考试信息，添加新考试。
- **管理问题**：添加或删除考试问题。
- **考试详情**：查看特定考试下的问题及其答案。


---

## 开发者
- **姓名：[qing]
- **邮箱：[tiangyn7@outlook.com]
- **GitHub：[https://github.com/tiangyn7](https://github.com/tiangyn7)


---

## 许可证
本项目基于 MIT 许可证开源。
