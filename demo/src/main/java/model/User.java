package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String password;
    private String email;  // 用户邮箱
    private String role;   // 用户角色, 可以是 "student" 或 "teacher"
}