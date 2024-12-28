<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="java.util.List" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>成绩详情</title>
            </head>

            <body>
                <h2>成绩详情 - 学生：${student.name}</h2>
                <p>考试名称：${exam.name}</p>
                <table border="1">
                    <tr>
                        <th>题目</th>
                        <th>学生答案</th>
                        <th>正确答案</th>
                        <th>得分</th>
                    </tr>
                    <c:forEach var="detail" items="${scoreDetails}">
                        <tr>
                            <td>${detail.questionText}</td>
                            <td>${detail.studentAnswer}</td>
                            <td>${detail.correctAnswer}</td>
                            <td>${detail.score}</td>
                        </tr>
                    </c:forEach>
                </table>
                <p>总分：${totalScore}</p>
            </body>

            </html>