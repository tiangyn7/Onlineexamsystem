package dao;

import model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    boolean registerUser(User user, Connection conn) throws SQLException;

    boolean registerUser(User user);
}