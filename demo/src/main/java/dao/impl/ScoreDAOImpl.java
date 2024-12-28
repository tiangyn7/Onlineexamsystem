package dao.impl;

import dao.ScoreDAO;
import model.Score;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAOImpl implements ScoreDAO {
    @Override
    public void saveScore(int examId, int studentId, int score) {
        String sql = "INSERT INTO scores (exam_id, student_id, score) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Score> getScoresByExamId(int examId) {
        List<Score> scores = new ArrayList<>();
        String sql = "SELECT u.username AS studentName, s.score, s.student_id " +
                "FROM scores s JOIN users u ON s.student_id = u.id WHERE s.exam_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setStudentName(rs.getString("studentName"));
                score.setScore(rs.getInt("score"));
                score.setStudentId(rs.getInt("student_id"));
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    @Override
    public Score getScoreDetail(int examId, int studentId) {
        Score score = new Score();
        String sql = "SELECT * FROM scores WHERE exam_id = ? AND student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, examId);
            pstmt.setInt(2, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                score.setScore(rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }
}
