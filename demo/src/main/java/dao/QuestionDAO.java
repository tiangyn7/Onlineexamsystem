package dao;

import model.Question;

import java.util.List;

public interface QuestionDAO {
    /**
     * 根据考试 ID 获取所有试题
     * @param examId 考试 ID
     * @return 试题列表
     */
    List<Question> getQuestionsByExamId(int examId);

    /**
     * 根据试题 ID 获取具体试题
     * @param questionId 试题 ID
     * @return 试题
     */
    Question getQuestionById(int questionId);
}
