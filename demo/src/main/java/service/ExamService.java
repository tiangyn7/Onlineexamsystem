package service;

import model.Exam;
import model.Question;

import java.util.List;

public interface ExamService {
    boolean createExam(Exam exam);
    List<Exam> getAllExams();
    Exam getExamById(int id);
    boolean addQuestionToExam(int examId, int questionId);
    boolean removeQuestionFromExam(int examId, int questionId);
    List<Question> getQuestionsByExamId(int examId); // 添加此方法声明

    List<Question> getQuestionsForExam(int i);
}
