package service;

import model.Question;

import java.util.List;

public interface QuestionService {
    boolean createQuestion(Question question);
    List<Question> getAllQuestions();
    Question getQuestionById(int id);
    boolean updateQuestion(Question question);
    boolean deleteQuestion(int id);
    List<Question> searchQuestions(String keyword, String type, String difficulty);
    List<Question> getQuestionsByExamId(int examId);
}
