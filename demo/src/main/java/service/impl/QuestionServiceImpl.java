package service.impl;

import model.Question;
import service.QuestionService;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public boolean createQuestion(Question question) {
        String sql = "INSERT INTO questions (question_text, correct_answer, score, type, difficulty) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getCorrectAnswer());
            pstmt.setInt(3, question.getScore());
            pstmt.setString(4, question.getType());
            pstmt.setString(5, question.getDifficulty());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                question.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("创建题目失败，无法获取生成的 ID");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // 或者使用日志框架记录错误
            return false;
        } finally {
            DBUtil.close(generatedKeys, pstmt, conn);
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        return searchQuestions(null, null, null);
    }

    @Override
    public Question getQuestionById(int id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapToQuestion(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 或者使用日志框架记录错误
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }
        return null;
    }

    @Override
    public boolean updateQuestion(Question question) {
        String sql = "UPDATE questions SET question_text = ?, correct_answer = ?, score = ?, type = ?, difficulty = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, question.getQuestionText());
            pstmt.setString(2, question.getCorrectAnswer());
            pstmt.setInt(3, question.getScore());
            pstmt.setString(4, question.getType());
            pstmt.setString(5, question.getDifficulty());
            pstmt.setInt(6, question.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // 或者使用日志框架记录错误
            return false;
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    @Override
    public boolean deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // 或者使用日志框架记录错误
            return false;
        } finally {
            DBUtil.close(pstmt, conn);
        }
    }

    @Override
    public List<Question> searchQuestions(String keyword, String type, String difficulty) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE question_text LIKE ? AND type LIKE ? AND difficulty LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            pstmt.setString(2, "%" + (type == null || type.isEmpty() ? "" : type) + "%");
            pstmt.setString(3, "%" + (difficulty == null || difficulty.isEmpty() ? "" : difficulty) + "%");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                questions.add(mapToQuestion(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 或者使用日志框架记录错误
        } finally {
            DBUtil.close(rs, pstmt, conn);
        }

        return questions;
    }

    @Override
    public List<Question> getQuestionsByExamId(int examId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.id, q.question_text, q.correct_answer, q.score, q.type, q.difficulty " +
                "FROM exam_questions eq " +
                "JOIN questions q ON eq.question_id = q.id " +
                "WHERE eq.exam_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, examId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setId(rs.getInt("id"));
                    question.setQuestionText(rs.getString("question_text"));
                    question.setCorrectAnswer(rs.getString("correct_answer"));
                    question.setScore(rs.getInt("score"));
                    question.setType(rs.getString("type"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private Question mapToQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setQuestionText(rs.getString("question_text"));
        question.setCorrectAnswer(rs.getString("correct_answer"));
        question.setScore(rs.getInt("score"));
        question.setType(rs.getString("type"));
        question.setDifficulty(rs.getString("difficulty"));
        question.setCreatedAt(rs.getTimestamp("created_at"));
        return question;
    }
}
