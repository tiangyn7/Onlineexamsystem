<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
			<%@ page import="model.User" %>
				<%@ page import="model.Exam" %>
					<%@ page import="java.util.List" %>

						<!DOCTYPE html>
						<html lang="zh">

						<head>
							<meta charset="UTF-8">
							<title>个人资料 - 测试页面</title>
							<script type="text/javascript">
								function checkLoginAndRedirect() {
									var isLoggedIn = "${not empty sessionScope.user}";
									if (!isLoggedIn) {
										alert("请先登录以使用该功能。");
										window.location.href = "${pageContext.request.contextPath}/login.jsp";
										return false;
									}
									return true;
								}
							</script>
						</head>

						<body>
							<div class="container">
								<h2>欢迎访问个人资料页面!</h2>

								<c:choose>
									<c:when test="${empty sessionScope.user}">
										<p>您当前为游客，请登录以获取完整体验。</p>
										<a href="${pageContext.request.contextPath}/login.jsp">前往登录</a>
									</c:when>
									<c:otherwise>
										<h3>欢迎, ${sessionScope.user.username}!</h3>
										<!-- 显示用户信息 -->
										<p>用户名: ${sessionScope.user.username}</p>
										<p>邮箱: ${sessionScope.user.email}</p>
										<p>角色: ${sessionScope.user.role}</p>

										<!-- 修改用户信息的表单 -->
										<form action="${pageContext.request.contextPath}/user?action=update"
											method="post" onsubmit="return checkLoginAndRedirect()">
											<input type="hidden" name="userId" value="${sessionScope.user.id}" />
											<label for="newUsername">新用户名:</label>
											<input type="text" id="newUsername" name="newUsername" required />
											<br />
											<label for="newEmail">新邮箱:</label>
											<input type="email" id="newEmail" name="newEmail" required />
											<br />
											<label for="newPassword">新密码:</label>
											<input type="password" id="newPassword" name="newPassword"
												placeholder="留空则不更改密码" />
											<br />
											<button type="submit">保存更改</button>
										</form>
									</c:otherwise>
								</c:choose>
							</div>
						</body>

						</html>