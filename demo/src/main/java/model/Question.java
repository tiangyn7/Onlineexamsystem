package model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class Question {
    private int id;
    private String questionText; // 对应于表中的 `question_text`
    private String correctAnswer; // 对应于表中的 `correct_answer`
    private int score; // 对应于表中的 `score`
    private String type; // 单选、多选、判断、简答，对应于表中的 `type`
    private String difficulty; // 难度级别（可选），对应于表中的 `difficulty`
    private Timestamp createdAt; // 创建时间，对应于表中的 `created_at`

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}