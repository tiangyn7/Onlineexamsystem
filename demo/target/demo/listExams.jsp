<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <% if (request.getAttribute("examList")==null) { response.sendRedirect("exam?action=list"); return; } %>

        <%@ page import="model.Exam" %>
            <%@ page import="java.util.List" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>考试列表</title>
                </head>

                <body>
                    <h2>考试列表</h2>

                    <table border="1">
                        <tr>
                            <th>考试名称</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>持续时间</th>
                            <th>操作</th>
                        </tr>
                        <% List<Exam> exams = (List<Exam>) request.getAttribute("examList");
                                if (exams != null && !exams.isEmpty()) {
                                for (Exam exam : exams) {
                                %>
                                <tr>
                                    <td>
                                        <%= exam.getName() %>
                                    </td>
                                    <td>
                                        <%= exam.getStartTime() %>
                                    </td>
                                    <td>
                                        <%= exam.getEndTime() %>
                                    </td>
                                    <td>
                                        <%= exam.getDuration() %> 分钟
                                    </td>
                                    <td><a href="manageExam.jsp?examId=<%= exam.getId() %>">管理</a></td>
                                </tr>
                                <% } } else { %>
                                    <tr>
                                        <td colspan="5">没有考试记录。</td>
                                    </tr>
                                    <% } %>
                    </table>

                    <a href="createExam.jsp">创建新考试</a>
                </body>

                </html>