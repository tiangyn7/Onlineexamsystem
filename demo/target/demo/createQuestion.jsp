<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="model.Question" %>
            <%@ page import="java.util.List" %>

                <html>

                <head>
                    <title>创建新题目</title>
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
                    <h1>创建新题目</h1>

                    <c:if test="${not empty errorMessage}">
                        <p class="error">${errorMessage}</p>
                    </c:if>
                    <c:if test="${not empty param.message}">
                        <p class="success">${param.message}</p>
                    </c:if>

                    <form action="question" method="post">
                        <input type="hidden" name="action" value="create">

                        <label for="questionText">题目内容:</label><br>
                        <textarea id="questionText" name="questionText" required></textarea><br>

                        <label for="correctAnswer">正确答案:</label><br>
                        <input type="text" id="correctAnswer" name="correctAnswer" required><br>

                        <label for="score">分值:</label><br>
                        <input type="number" id="score" name="score" min="1" required><br>

                        <label for="type">类型:</label><br>
                        <select id="type" name="type" required>
                            <option value="单选">单选</option>
                            <option value="多选">多选</option>
                            <option value="判断">判断</option>
                            <option value="简答">简答</option>
                        </select><br>

                        <label for="difficulty">难度:</label><br>
                        <select id="difficulty" name="difficulty">
                            <option value="">无</option>
                            <option value="容易">容易</option>
                            <option value="中等">中等</option>
                            <option value="困难">困难</option>
                        </select><br>

                        <input type="submit" value="创建题目">
                    </form>
                </body>

                </html>