package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AnswerService;
import service.ScoreService;
import service.impl.AnswerServiceImpl;
import service.impl.ScoreServiceImpl;

@WebServlet("/submitExam")
public class SubmitExamServlet extends HttpServlet {
    private final AnswerService answerService = new AnswerServiceImpl();
    private final ScoreService scoreService = new ScoreServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentId = (int) request.getSession().getAttribute("userId");
        int examId = Integer.parseInt(request.getParameter("examId"));
        Map<Integer, String> answers = new HashMap<>();

        // 获取提交的答案
        request.getParameterMap().forEach((key, value) -> {
            if (key.startsWith("answer_")) {
                int questionId = Integer.parseInt(key.substring(7));
                answers.put(questionId, value[0]);
            }
        });

        // 保存答案到数据库
        answerService.saveAnswers(studentId, examId, answers);

        // 自动计算成绩并保存
        int totalScore = scoreService.calculateScore(examId, studentId);

        // 将总分作为参数传递给成绩报告页面
        response.sendRedirect(
                "scoreReport.jsp?examId=" + examId + "&studentId=" + studentId + "&totalScore=" + totalScore);
    }
}