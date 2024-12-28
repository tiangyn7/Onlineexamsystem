package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBUtil {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3309/webwork?useSSL=false&serverTimezone=UTC");
        config.setUsername("demon");
        config.setPassword("12747180");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setPoolName("HikariCPDatasource");
        config.setMinimumIdle(5);
        config.setIdleTimeout(180000);
        config.setMaximumPoolSize(10);
        config.setAutoCommit(true);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        config.setConnectionTestQuery("SELECT 1");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //  close 方法来关闭 ResultSet, PreparedStatement 和 Connection
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 如果没有 ResultSet，可以使用这个重载的方法
    public static void close(PreparedStatement pst, Connection conn) {
        close(null, pst, conn);
    }

    // 只关闭 Connection 的方法也可以保留
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println("数据库连接成功！");
        } catch (SQLException e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }
}