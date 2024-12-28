<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


			<!DOCTYPE html>
			<html lang="en">

			<head>
				<meta charset="UTF-8">
				<meta http-equiv="X-UA-Compatible" content="IE=edge">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<link rel="stylesheet" href="static/css/a/login.css">
				<script src="https://at.alicdn.com/t/c/font_4068759_s6hfignxugn.js"></script>
				<title>
					Register
				</title>
			</head>

			<body class="align">

				<div class="grid">

					<form action="user?action=register" method="POST" class="form login">


						<!-- Username -->
						<div class="form__field">
							<label for="login__username">
								<svg class="icon">
									<use xlink:href="#icon-yonghu">
									</use>
								</svg>
								<span class="hidden">Username</span>
							</label>
							<input autocomplete="username" id="login__username" type="text" name="username"
								class="form__input" placeholder="Username" required maxlength="10">
						</div>

						<!-- Password -->
						<div class="form__field">
							<label for="login__password">
								<svg class="icon">
									<use xlink:href="#icon-mimasuo">
									</use>
								</svg>
								<span class="hidden">Password</span>
							</label>
							<input id="login__password" type="password" name="password" class="form__input"
								placeholder="Password" required>
						</div>

						<!-- Confirm Password -->
						<div class="form__field">
							<label for="login__password_confirm">
								<svg class="icon">
									<use xlink:href="#icon-mimasuo">
									</use>
								</svg>
								<span class="hidden">Confirm
									Password</span>
							</label>
							<input id="login__password_confirm" type="password" name="password2" class="form__input"
								placeholder="Confirm the password" required>
						</div>

						<!-- Email -->
						<div class="form__field">
							<label for="login__email">
								<svg class="icon">
									<use xlink:href="#icon-email">
									</use>
								</svg>
								<span class="hidden">Email</span>
							</label>
							<input id="login__email" type="email" name="email" class="form__input" placeholder="Email"
								required>
						</div>

						<!-- Submit Button -->
						<div class="form__field">
							<input type="submit" value="Sign up">
						</div>

					</form>

					<!-- Link to Login Page -->
					<p class="text--center">
						Already
						have
						an
						account?
						<a href="./login.jsp">Sign
							in
							now</a>
						<svg class="icon">
							<use xlink:href="#icon-arrow-right">
							</use>
						</svg>
					</p>
				</div>

				<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
				<script src="static/js/a/jquery-1.12.4.js"></script>
				<script>
					document.querySelector('form').addEventListener('submit', function (event) {
						var password = document.getElementById('login__password').value;
						var confirmPassword = document.getElementById('login__password_confirm').value;

						if (password !== confirmPassword) {
							alert("密码不一致，请重新输入。");
							event.preventDefault(); // 阻止表单提交
						}
					});
				</script>
			</body>

			</html>