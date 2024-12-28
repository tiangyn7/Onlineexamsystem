package dao;

import model.Score;

import java.util.List;

public interface ScoreDAO {
    void saveScore(int examId, int studentId, int score);
    List<Score> getScoresByExamId(int examId);
    Score getScoreDetail(int examId, int studentId);
}

