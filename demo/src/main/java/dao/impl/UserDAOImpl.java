package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.UserDAO;
import model.User;
import utils.DBUtil; // 假设你有一个 DBUtil 工具类来管理数据库连接

public class UserDAOImpl implements UserDAO {

    /**
     * 根据用户名查找用户。
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回该用户对象；否则返回 null。
     */
    @Override
    public User findByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 准备 SQL 查询语句
            String sql = "SELECT * FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            // 设置查询参数
            pstmt.setString(1, username);
            // 执行查询并获取结果集
            rs = pstmt.executeQuery();

            // 如果查询到记录，则创建 User 对象并填充数据
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password")); // 注意：这里直接比较明文密码
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email")); // 假设你的 User 模型中有 email 字段
            }
        } catch (SQLException e) {
            // 捕获并打印 SQL 异常信息
            e.printStackTrace();
        } finally {
            // 确保资源被正确关闭
            DBUtil.close(rs, pstmt, conn);
        }

        return user;
    }

    /**
     * 根据用户名和密码查找用户。
     *
     * @param username 用户名
     * @param password 密码（明文）
     * @return 如果找到对应的用户则返回该用户对象；否则返回 null。
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // 直接比较明文密码
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password")); // 注意：这里直接比较明文密码
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email")); // 假设你的 User 模型中有 email 字段
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return user;
    }

    @Override
    public boolean registerUser(User user, Connection conn) throws SQLException {
        return false;
    }

    /**
     * 注册新用户到数据库。
     *
     * @param user 包含新用户信息的对象
     * @return 如果注册成功返回 true；否则返回 false。
     */
    @Override
    public boolean registerUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setString(4, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            // 捕获并处理 SQL 异常信息
            System.err.println("Error: 用户名或邮箱已存在");
            // 可以在这里设置请求属性，并转发到注册页面显示错误信息
            e.printStackTrace(); // 在生产环境中应避免直接打印堆栈跟踪
        } finally {
            // 确保资源被正确关闭
            DBUtil.close(null, pstmt, conn);
        }

        return success;
    }
}