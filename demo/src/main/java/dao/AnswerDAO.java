package dao;

import java.util.Map;

public interface AnswerDAO {
    /**
     * 根据考试 ID 和学生 ID 获取学生提交的答案
     * @param examId 考试 ID
     * @param studentId 学生 ID
     * @return 试题 ID 和对应答案的映射
     */
    Map<Integer, String> getAnswersByExamAndStudent(int examId, int studentId);

    /**
     * 保存学生提交的答案
     * @param studentId 学生 ID
     * @param examId 考试 ID
     * @param answers 试题 ID 和对应答案的映射
     */
    void saveAnswers(int studentId, int examId, Map<Integer, String> answers);
}
