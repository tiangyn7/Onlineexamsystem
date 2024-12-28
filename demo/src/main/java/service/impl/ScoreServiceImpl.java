package service.impl;

import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.ScoreDAO;
import dao.impl.AnswerDAOImpl;
import dao.impl.QuestionDAOImpl;
import dao.impl.ScoreDAOImpl;
import model.Question;
import model.Score;
import service.ScoreService;

import java.util.List;
import java.util.Map;

public class ScoreServiceImpl implements ScoreService {
    private final AnswerDAO answerDAO = new AnswerDAOImpl();
    private final QuestionDAO questionDAO = new QuestionDAOImpl();
    private final ScoreDAO scoreDAO = new ScoreDAOImpl();

    @Override
    public int calculateScore(int examId, int studentId) {
        // 获取学生提交的答案
        Map<Integer, String> studentAnswers = answerDAO.getAnswersByExamAndStudent(examId, studentId);

        // 获取考试中的所有试题及正确答案
        List<Question> questions = questionDAO.getQuestionsByExamId(examId);

        int totalScore = 0;
        for (Question question : questions) {
            String correctAnswer = question.getCorrectAnswer();
            String studentAnswer = studentAnswers.get(question.getId());
            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(studentAnswer)) {
                totalScore += question.getScore();
            }
        }

        // 保存成绩
        scoreDAO.saveScore(examId, studentId, totalScore);
        return totalScore;
    }

    @Override
    public List<Score> getScoresByExamId(int examId) {
        return scoreDAO.getScoresByExamId(examId);
    }

    @Override
    public Score getScoreDetail(int examId, int studentId) {
        return scoreDAO.getScoreDetail(examId, studentId);
    }
}


