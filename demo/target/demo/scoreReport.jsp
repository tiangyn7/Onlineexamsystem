<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="java.util.List" %>
            <%@ page import="java.util.Map" %>
                <%@ page import="java.util.HashMap" %>
                    <%@ page import="java.util.ArrayList" %>
                        <%@ page import="java.util.Arrays" %>
                            <%@ page import="java.util.Collections" %>
                                <%@ page import="java.util.Comparator" %>

                                    <!DOCTYPE html>
                                    <html>

                                    <head>
                                        <title>成绩报告</title>
                                    </head>

                                    <body>
                                        <h2>成绩报告 - 考试名称：${exam.name}</h2>
                                        <table border="1">
                                            <tr>
                                                <th>学生姓名</th>
                                                <th>总分</th>
                                                <th>查看详情</th>
                                            </tr>
                                            <c:forEach var="score" items="${scores}">
                                                <tr>
                                                    <td>${score.studentName}</td>
                                                    <td>${score.score}</td>
                                                    <td><a
                                                            href="scoreDetail.jsp?examId=${exam.id}&studentId=${score.studentId}">查看详情</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </body>

                                    </html>