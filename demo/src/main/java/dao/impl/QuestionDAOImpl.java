package dao.impl;

import dao.QuestionDAO;
import model.Question;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAOImpl implements QuestionDAO {

    @Override
    public List<Question> getQuestionsByExamId(int examId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.id, q.question_text, q.correct_answer, q.score, q.type, q.difficulty " +
                "FROM exam_questions eq JOIN questions q ON eq.question_id = q.id WHERE eq.exam_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setCorrectAnswer(rs.getString("correct_answer"));
                question.setScore(rs.getInt("score"));
                question.setType(rs.getString("type"));
                question.setDifficulty(rs.getString("difficulty"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public Question getQuestionById(int questionId) {
        Question question = null;
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                question = new Question();
                question.setId(rs.getInt("id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setCorrectAnswer(rs.getString("correct_answer"));
                question.setScore(rs.getInt("score"));
                question.setType(rs.getString("type"));
                question.setDifficulty(rs.getString("difficulty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }
}
