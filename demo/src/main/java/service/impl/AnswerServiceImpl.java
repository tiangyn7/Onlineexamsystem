package service.impl;

import dao.AnswerDAO;
import dao.impl.AnswerDAOImpl;
import service.AnswerService;

import java.util.Map;

public class AnswerServiceImpl implements AnswerService {
    private final AnswerDAO answerDAO = new AnswerDAOImpl();

    @Override
    public void saveAnswers(int studentId, int examId, Map<Integer, String> answers) {
        answerDAO.saveAnswers(studentId, examId, answers);
    }

    @Override
    public Map<Integer, String> getAnswersByExamAndStudent(int examId, int studentId) {
        return answerDAO.getAnswersByExamAndStudent(examId, studentId);
    }
}
