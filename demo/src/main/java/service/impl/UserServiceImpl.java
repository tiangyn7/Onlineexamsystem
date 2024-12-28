package service.impl;

import model.User;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import service.UserService;
import utils.DBUtil; // 确保导入的是正确的 DBUtil 类

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public User login(String username, String password) {
        // 调用 DAO 层的方法来查找用户并验证密码（明文）
        return userDAO.findByUsernameAndPassword(username, password);
    }

    @Override
    public User login(String username, String password, HttpServletRequest request) {
        // 调用 DAO 层的方法来查找用户并验证密码（明文）
        User user = userDAO.findByUsernameAndPassword(username, password);

        if (user != null) {
            // 登录成功，将用户信息存储到 session 中
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        } else {
            // 登录失败，设置错误信息
            request.setAttribute("error", "无效的用户名或密码");
        }

        return user;
    }

    @Override
    public boolean register(User user) {
        try (Connection conn = DBUtil.getConnection(); // 使用 DBUtil 获取连接
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)")) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // 保存明文密码
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getRole());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public boolean updateUser(User user) {
        // 检查是否有新密码需要更新
        String newPassword = user.getPassword();
        boolean updatePassword = newPassword != null && !newPassword.trim().isEmpty();

        String sql = "UPDATE users SET username = ?, email = ?";
        if (updatePassword) {
            sql += ", password = ?";
        }
        sql += " WHERE id = ?";

        try (Connection conn = DBUtil.getConnection(); // 使用 DBUtil 获取连接
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            if (updatePassword) {
                pstmt.setString(3, newPassword);
                pstmt.setInt(4, user.getId());
            } else {
                pstmt.setInt(3, user.getId());
            }

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}