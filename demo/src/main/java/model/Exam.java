package model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Exam {
    private int id; // 添加 id 属性
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private String description;
    private List<Question> questions; // 修改为 List<Question>

    // 其他 getter 和 setter 方法
}