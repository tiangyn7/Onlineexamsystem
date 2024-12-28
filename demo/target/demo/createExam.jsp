<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="model.Exam" %>
        <%@ page import="model.Question" %>
            <%@ page import="java.util.List" %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>创建考试</title>
                </head>

                <body>
                    <h2>创建考试</h2>

                    <!-- 显示错误消息（如果有） -->
                    <% String errorMessage=(String) request.getAttribute("errorMessage"); if (errorMessage !=null) { %>
                        <p style="color: red;">
                            <%= errorMessage %>
                        </p>
                        <% } %>

                            <form action="exam" method="post">
                                <input type="hidden" name="action" value="create">
                                <label for="name">考试名称：</label>
                                <input type="text" id="name" name="name" required><br>
                                <label for="startTime">开始时间：</label>
                                <input type="datetime-local" id="startTime" name="startTime" required><br>
                                <label for="endTime">结束时间：</label>
                                <input type="datetime-local" id="endTime" name="endTime" required><br>
                                <label for="duration">持续时间（分钟）：</label>
                                <input type="number" id="duration" name="duration" required><br>
                                <label for="description">考试描述：</label>
                                <textarea id="description" name="description"></textarea><br>
                                <button type="submit">提交</button>
                            </form>
                            <a href="listExams.jsp">返回考试列表</a>
                </body>

                </html>