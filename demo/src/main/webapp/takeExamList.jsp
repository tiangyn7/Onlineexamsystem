<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="java.util.List" %>
            <%@ page import="java.util.Map" %>
                <%@ page import="java.util.HashMap" %>
                    <%@ page import="java.util.ArrayList" %>
                        <%@ page import="java.util.Arrays" %>

                            <!DOCTYPE html>
                            <html>

                            <head>
                                <title>选择考试</title>
                            </head>

                            <body>
                                <h2>待参加的考试</h2>
                                <table border="1">
                                    <tr>
                                        <th>考试名称</th>
                                        <th>开始时间</th>
                                        <th>结束时间</th>
                                        <th>操作</th>
                                    </tr>
                                    <c:forEach var="exam" items="${availableExams}">
                                        <tr>
                                            <td>${exam.name}</td>
                                            <td>${exam.startTime}</td>
                                            <td>${exam.endTime}</td>
                                            <td><a href="takeExam?examId=${exam.id}">参加考试</a></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </body>

                            </html>