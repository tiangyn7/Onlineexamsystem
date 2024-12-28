<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="model.Question" %>
            <%@ page import="java.util.List" %>
                <%@ page import="model.Exam" %>
                    <%@ page import="model.User" %>
                        <%@ page import="model.Question" %>
                            <%@ page import="java.util.List" %>

                                <html>

                                <head>
                                    <title>编辑题目</title>
                                    <style>
                                        .error,
                                        .success {
                                            color: red;
                                            font-weight: bold;
                                        }

                                        .success {
                                            color: green;
                                        }
                                    </style>
                                </head>

                                <body>
                                    <h1>编辑题目</h1>

                                    <c:if test="${not empty errorMessage}">
                                        <p class="error">${errorMessage}</p>
                                    </c:if>
                                    <c:if test="${not empty param.message}">
                                        <p class="success">${param.message}</p>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${not empty question}">
                                            <form action="question" method="post">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="id" value="${question.id}">

                                                <label for="questionText">题目内容:</label><br>
                                                <textarea id="questionText" name="questionText"
                                                    required>${question.questionText}</textarea><br>

                                                <label for="correctAnswer">正确答案:</label><br>
                                                <input type="text" id="correctAnswer" name="correctAnswer"
                                                    value="${question.correctAnswer}" required><br>

                                                <label for="score">分值:</label><br>
                                                <input type="number" id="score" name="score" value="${question.score}"
                                                    min="1" required><br>

                                                <label for="type">类型:</label><br>
                                                <select id="type" name="type" required>
                                                    <option value="单选" ${question.type=='单选' ? 'selected' : '' }>单选
                                                    </option>
                                                    <option value="多选" ${question.type=='多选' ? 'selected' : '' }>多选
                                                    </option>
                                                    <option value="判断" ${question.type=='判断' ? 'selected' : '' }>判断
                                                    </option>
                                                    <option value="简答" ${question.type=='简答' ? 'selected' : '' }>简答
                                                    </option>
                                                </select><br>

                                                <label for="difficulty">难度:</label><br>
                                                <select id="difficulty" name="difficulty">
                                                    <option value="" ${empty question.difficulty ? 'selected' : '' }>无
                                                    </option>
                                                    <option value="容易" ${question.difficulty=='容易' ? 'selected' : '' }>
                                                        容易</option>
                                                    <option value="中等" ${question.difficulty=='中等' ? 'selected' : '' }>
                                                        中等</option>
                                                    <option value="困难" ${question.difficulty=='困难' ? 'selected' : '' }>
                                                        困难</option>
                                                </select><br>

                                                <input type="submit" value="保存修改">
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="error">找不到要编辑的题目。</p>
                                        </c:otherwise>
                                    </c:choose>
                                </body>

                                </html>