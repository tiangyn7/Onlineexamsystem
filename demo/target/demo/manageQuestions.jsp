<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page import="model.Exam" %>
            <%@ page import="model.Question" %>
                <%@ page import="java.util.List" %>

                    <html>

                    <head>
                        <title>管理题目</title>
                    </head>

                    <body>
                        <h1>管理题目</h1>

                        <!-- 搜索表单 -->
                        <form action="question" method="post">
                            <input type="hidden" name="action" value="search">
                            关键词: <input type="text" name="keyword">
                            类型: <select name="type">
                                <option value="">全部</option>
                                <option value="单选">单选</option>
                                <option value="多选">多选</option>
                                <option value="判断">判断</option>
                                <option value="简答">简答</option>
                            </select>
                            难度: <select name="difficulty">
                                <option value="">全部</option>
                                <option value="容易">容易</option>
                                <option value="中等">中等</option>
                                <option value="困难">困难</option>
                            </select>
                            <input type="submit" value="搜索">
                        </form>

                        <!-- 显示题目列表 -->
                        <table border="1">
                            <tr>
                                <th>ID</th>
                                <th>题目内容</th>
                                <th>正确答案</th>
                                <th>分值</th>
                                <th>类型</th>
                                <th>难度</th>
                                <th>操作</th>
                            </tr>
                            <c:forEach var="question" items="${questionList}">
                                <tr>
                                    <td>${question.id}</td>
                                    <td>${question.questionText}</td>
                                    <td>${question.correctAnswer}</td>
                                    <td>${question.score}</td>
                                    <td>${question.type}</td>
                                    <td>${question.difficulty}</td>
                                    <td>
                                        <!-- 修改编辑链接以通过 QuestionServlet 处理 -->
                                        <a href="question?action=edit&id=${question.id}">编辑</a>
                                        <form style="display:inline;" action="question" method="post">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id" value="${question.id}">
                                            <input type="submit" value="删除">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <!-- 创建新题目链接 -->
                        <a href="createQuestion.jsp">创建新题目</a>
                    </body>

                    </html>