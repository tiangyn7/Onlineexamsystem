<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page language="java" import="java.util.*, com.example.model.*" pageEncoding="UTF-8" %>

        <!DOCTYPE html>
        <html lang="zh">

        <head>
            <meta charset="UTF-8">
            <title>考试详情</title>
        </head>

        <body>
            <h2>考试详情</h2>

            <% // 从请求中获取 exam 和 examQuestions Exam exam=(Exam) request.getAttribute("exam"); List<Question> questions =
                (List<Question>) request.getAttribute("examQuestions");

                    if (exam == null) {
                    %>
                    <p>未找到考试详情，请检查考试 ID。</p>
                    <% } else { %>
                        <p>考试名称：<%= exam.getName() %>
                        </p>
                        <p>开始时间：<%= exam.getStartTime() %>
                        </p>
                        <p>结束时间：<%= exam.getEndTime() %>
                        </p>
                        <p>持续时间：<%= exam.getDuration() %> 分钟</p>
                        <p>描述：<%= exam.getDescription() %>
                        </p>

                        <h3>试题列表</h3>
                        <table border="1">
                            <tr>
                                <th>试题内容</th>
                                <th>操作</th>
                            </tr>
                            <% if (questions !=null && !questions.isEmpty()) { for (Question question : questions) { %>
                                <tr>
                                    <td>
                                        <%= question.getQuestionText() %>
                                    </td>
                                    <td>
                                        <a
                                            href="exam?action=removeQuestionFromExam&examId=<%= exam.getId() %>&questionId=<%= question.getId() %>">移除</a>
                                    </td>
                                </tr>
                                <% } } else { %>
                                    <tr>
                                        <td colspan="2">暂无试题</td>
                                    </tr>
                                    <% } %>
                        </table>
                        <a href="addQuestionToExam.jsp?examId=<%= exam.getId() %>">添加试题</a>
                        <% } %>

                            <a href="listExams.jsp">返回考试列表</a>
        </body>

        </html>