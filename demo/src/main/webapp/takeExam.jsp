<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="java.util.List" %>
            <%@ page import="java.util.Map" %>
                <%@ page import="java.util.HashMap" %>
                    <%@ page import="java.util.ArrayList" %>

                        <!DOCTYPE html>
                        <html>

                        <head>
                            <title>${exam.name} - 作答</title>
                            <script>
                                let timer = ${ exam.duration } * 60; // 考试时长（分钟）
                                setInterval(function () {
                                    const minutes = Math.floor(timer / 60);
                                    const seconds = timer % 60;
                                    document.getElementById('timer').innerText = `${minutes}:${seconds}`;
                                    timer--;
                                    if (timer < 0) {
                                        document.getElementById('examForm').submit(); // 时间到自动提交
                                    }
                                }, 1000);
                            </script>
                        </head>

                        <body>
                            <h2>${exam.name}</h2>
                            <p>考试时长：${exam.duration} 分钟</p>
                            <p>剩余时间：<span id="timer"></span></p>
                            <form id="examForm" action="submitExam" method="post">
                                <input type="hidden" name="examId" value="${exam.id}">
                                <c:forEach var="question" items="${questions}">
                                    <div>
                                        <label>${question.questionText}</label><br>
                                        <input type="text" name="answer_${question.id}" required>
                                    </div>
                                </c:forEach>
                                <button type="submit">提交</button>
                            </form>
                        </body>

                        </html>