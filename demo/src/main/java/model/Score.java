package model;

import lombok.Getter;
import lombok.Setter;

// 定义成绩实体类
@Setter
@Getter
public class Score {
    // Getter 和 Setter 方法
    private int studentId;        // 学生ID
    private String studentName;   // 学生姓名
    private int examId;           // 考试ID
    private int score;            // 总分

}
