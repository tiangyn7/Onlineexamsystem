线上考试系统技术文档

一、系统概述

线上考试系统是一个选择题和考试管理平台，用于学生考试和考试资源管理。系统提供以下功能：

考试列表显示和查询

查看考试详情，包括问题列表

增加和删除问题

考试与问题的关联

二、使用环境

开发环境

程序设计语言: Java (JDK 11)

系统框架: Servlet + JSP

程序构构: Maven Project

数据库: MySQL 8.0

导入包: JDBC, MySQL Connector

使用业务和服务器

实验环境: Tomcat 9.0

数据库应用功能: 创建、查询、关联表

三、文件结构

该项目的文件结构如下：

└—— src
    ├—— main
    │   └—— java
    │       └—— controller
    │       │       ExamServlet.java
    │       │       QuestionServlet.java
    │       └—— service
    │       │       ExamService.java
    │       │       QuestionService.java
    │       └—— service.impl
    │       │       ExamServiceImpl.java
    │       │       QuestionServiceImpl.java
    │       └—— model
    │               Exam.java
    │               Question.java
    │
    └—— webapp
        └—— jsp
                listExams.jsp
                manageExam.jsp
                manageQuestions.jsp

四、功能流程

1. 查看考试列表

流程说明：

请求 URL: /exam?action=list

Servlet ExamServlet 进入分析功能定义，调用服务层函数 listExams，获取数据库考试列表，并返回 JSP。

2. 考试详情查询

流程说明：

请求 URL: /manageExam.jsp?examId=xxx

Servlet 中调用 getExamById(examId) 和 getQuestionsForExam(examId) ，将考试和相关问题数据传递给 JSP。

3. 新增问题

流程说明：

请求 URL: /addQuestion.jsp

完成前端表单提交后，Servlet 调用服务层新增问题函数，将信息存入数据库，然后重新调用查询推送 JSP页面。

五、数据库设计

1. 考试表 exams

字段名

类型

描述

id

INT

主键，默认自增

name

VARCHAR(255)

考试名称

start_time

DATETIME

开始时间

end_time

DATETIME

结束时间

description

TEXT

考试描述

2. 问题表 questions

字段名

类型

描述

id

INT

主键，默认自增

question_text

TEXT

问题内容

correct_answer

VARCHAR(255)

正确答案

score

INT

分值

type

VARCHAR(50)

问题类型（单选、多选）

3. 考试与问题关联表 exam_questions

字段名

类型

描述

id

INT

主键，自增

exam_id

INT

对应考试 id

question_id

INT

对应问题 id

4. 模块实现地址

数据库操作在 service.impl 中实现。

六、控制器和代码流程

1. 控制器 (Servlet)

ExamServlet.java：完成考试相关功能

QuestionServlet.java：完成问题管理功能

2. 服务层

添加问题：通过 addQuestion()

查询考试：通过 getExamById()和其他调用

3. JSP 渲染

字段和问题详情通过 ${exam.xxx} 和 forEach 展示。
