package service;

import model.Score;

import java.util.List;

public interface ScoreService {
    int calculateScore(int examId, int studentId); // 计算成绩
    List<Score> getScoresByExamId(int examId);    // 获取成绩列表
    Score getScoreDetail(int examId, int studentId); // 获取学生成绩详情
}

