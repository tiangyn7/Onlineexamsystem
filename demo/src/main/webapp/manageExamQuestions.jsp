<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="model.Question" %>
            <%@ page import="model.Exam" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>管理考试试题</title>
                </head>

                <body>
                    <h2>管理考试试题</h2>
                    <p>考试名称：<%= request.getAttribute("examName") %>
                    </p>

                    <h3>当前已关联的试题</h3>
                    <table border="1">
                        <tr>
                            <th>试题内容</th>
                            <th>操作</th>
                        </tr>
                        <% List<Question> questions = (List<Question>) request.getAttribute("questions");
                                if (questions != null && !questions.isEmpty()) {
                                for (Question question : questions) {
                                %>
                                <tr>
                                    <td>
                                        <%= question.getQuestionText() %>
                                    </td>
                                    <td>
                                        <a href="exam?action=removeQuestionFromExam&examId=<%= request.getAttribute("
                                            examId") %>&questionId=<%= question.getId() %>">移除</a>
                                    </td>
                                </tr>
                                <% } } else { %>
                                    <tr>
                                        <td colspan="2">没有关联的试题。</td>
                                    </tr>
                                    <% } %>
                    </table>

                    <h3>添加试题到考试</h3>
                    <form action="exam" method="post">
                        <input type="hidden" name="action" value="addQuestionToExam">
                        <input type="hidden" name="examId" value="<%= request.getAttribute(" examId") %>">
                        <select name="questionId">
                            <% List<Question> allQuestions = (List<Question>) request.getAttribute("allQuestions");
                                    if (allQuestions != null && !allQuestions.isEmpty()) {
                                    for (Question question : allQuestions) {
                                    %>
                                    <option value="<%= question.getId() %>">
                                        <%= question.getQuestionText() %>
                                    </option>
                                    <% } } %>
                        </select>
                        <button type="submit">添加</button>
                    </form>
                </body>

                </html>