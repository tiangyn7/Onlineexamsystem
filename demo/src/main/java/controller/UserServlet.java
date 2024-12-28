package controller;

import java.io.IOException;
import java.io.Serial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持 GET 请求，请使用 POST");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 打印会话信息到日志
            System.out.println("User logged in: " + user.getUsername());
            System.out.println("Session ID: " + session.getId());

            // 跳转到选择页面
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        } else {
            request.setAttribute("error", "无效的用户名或密码。");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("password2");
        String email = request.getParameter("email");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "密码不一致。");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole("student"); // 默认为学生角色

        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            request.setAttribute("error", "该用户名已被占用。");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (userService.register(newUser)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.setAttribute("error", "注册失败，请稍后再试。");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取当前会话
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;

        // 验证用户是否登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 获取并验证表单参数
        String userIdParam = request.getParameter("userId");
        if (userIdParam == null || userIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "用户 ID 是必填项");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "无效的用户 ID 格式");
            return;
        }

        // 验证用户权限
        if (userId != user.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "不允许修改其他用户的资料");
            return;
        }

        // 获取并更新用户信息
        String newUsername = request.getParameter("newUsername");
        String newEmail = request.getParameter("newEmail");

        if (newUsername == null || newUsername.trim().isEmpty() || newEmail == null || newEmail.trim().isEmpty()) {
            request.setAttribute("error", "用户名和邮箱不能为空");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }

        user.setUsername(newUsername);
        user.setEmail(newEmail);

        // 更新用户信息
        if (userService.updateUser(user)) {
            response.sendRedirect(request.getContextPath() + "/profile.jsp");
        } else {
            request.setAttribute("error", "更新失败，请稍后再试。");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
    }

}
