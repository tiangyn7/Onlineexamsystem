package service;

import javax.servlet.http.HttpServletRequest;

import model.User;

public interface UserService {
    User login(String username, String password, HttpServletRequest request);

    User login(String username, String password);

    boolean register(User user);

    User findByUsername(String username);

    boolean updateUser(User user);
}