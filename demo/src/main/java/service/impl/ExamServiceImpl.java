package service.impl;

import model.Exam;
import model.Question;
import service.ExamService;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamServiceImpl implements ExamService {

    /**
     * 创建考试
     *
     * @param exam 考试对象
     * @return 是否创建成功
     */
    @Override
    public boolean createExam(Exam exam) {
        String sql = "INSERT INTO exams (name, start_time, end_time, duration, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置参数
            stmt.setString(1, exam.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(exam.getStartTime()));
            stmt.setTimestamp(3, Timestamp.valueOf(exam.getEndTime()));
            stmt.setInt(4, exam.getDuration());
            stmt.setString(5, exam.getDescription());

            // 执行更新
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有考试
     *
     * @return 考试列表
     */
    @Override
    public List<Exam> getAllExams() {
        String sql = "SELECT id, name, start_time, end_time, duration, description FROM exams";
        List<Exam> exams = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setId(rs.getInt("id"));
                exam.setName(rs.getString("name"));
                exam.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                exam.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                exam.setDuration(rs.getInt("duration"));
                exam.setDescription(rs.getString("description"));
                exams.add(exam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exams;
    }



    /**
     * 根据考试ID获取考试
     *
     * @param id 考试ID
     * @return 考试对象
     */
    @Override
    public Exam getExamById(int id) {
        String sql = "SELECT id, name, start_time, end_time, duration, description FROM exams WHERE id = ?";
        Exam exam = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exam = new Exam();
                    exam.setId(rs.getInt("id"));
                    exam.setName(rs.getString("name"));
                    exam.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                    exam.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                    exam.setDuration(rs.getInt("duration"));
                    exam.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exam;
    }



    /**
     * 根据考试ID获取相关的试题
     *
     * @param examId 考试ID
     * @return 试题列表
     */
    @Override
    public List<Question> getQuestionsByExamId(int examId) {
        String sql = "SELECT q.id, q.question_text, q.correct_answer, q.score, q.type FROM questions q "
                + "JOIN exam_questions eq ON q.id = eq.question_id WHERE eq.exam_id = ?";
        List<Question> questions = new ArrayList<>();
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

    @Override
    public List<Question> getQuestionsForExam(int examId) {
        return List.of();
    }


    /**
     * 将试题添加到考试
     *
     * @param examId     考试ID
     * @param questionId 试题ID
     * @return 是否添加成功
     */
    @Override
    public boolean addQuestionToExam(int examId, int questionId) {
        String checkSql = "SELECT COUNT(*) FROM exam_questions WHERE exam_id = ? AND question_id = ?";
        String insertSql = "INSERT INTO exam_questions (exam_id, question_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            // 检查是否已存在
            checkStmt.setInt(1, examId);
            checkStmt.setInt(2, questionId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // 如果已存在，不插入，直接返回 true
                    return true;
                }
            }

            // 插入新记录
            insertStmt.setInt(1, examId);
            insertStmt.setInt(2, questionId);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 从考试中移除试题
     *
     * @param examId     考试ID
     * @param questionId 试题ID
     * @return 是否移除成功
     */
    @Override
    public boolean removeQuestionFromExam(int examId, int questionId) {
        String sql = "DELETE FROM exam_questions WHERE exam_id = ? AND question_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, examId);
            stmt.setInt(2, questionId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
