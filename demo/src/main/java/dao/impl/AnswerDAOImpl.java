package dao.impl;

import dao.AnswerDAO;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AnswerDAOImpl implements AnswerDAO {

    @Override
    public Map<Integer, String> getAnswersByExamAndStudent(int examId, int studentId) {
        Map<Integer, String> answers = new HashMap<>();
        String sql = "SELECT question_id, answer FROM answers WHERE exam_id = ? AND student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            pstmt.setInt(2, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String answer = rs.getString("answer");
                answers.put(questionId, answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public void saveAnswers(int studentId, int examId, Map<Integer, String> answers) {
        String sql = "INSERT INTO answers (student_id, exam_id, question_id, answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Integer, String> entry : answers.entrySet()) {
                pstmt.setInt(1, studentId);
                pstmt.setInt(2, examId);
                pstmt.setInt(3, entry.getKey());
                pstmt.setString(4, entry.getValue());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
