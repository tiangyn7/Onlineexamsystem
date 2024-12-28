<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="model.User" %>
            <%@ page import="model.Exam" %>
                <%@ page import="java.util.List" %>

                    <html>

                    <head>
                        <title>Dashboard</title>
                    </head>

                    <body>
                        <h2>欢迎，<%= ((User) session.getAttribute("user")).getUsername() %>
                        </h2>
                        <p>请选择要进入的页面：</p>
                        <ul>
                            <li><a href="createExam.jsp">创建考试</a></li>
                            <li><a href="manageExam.jsp">管理考试</a></li>
                            <li><a href="profile.jsp">个人信息</a></li>
                        </ul>
                    </body>

                    </html>