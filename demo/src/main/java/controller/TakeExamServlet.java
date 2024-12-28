package controller;

import model.Exam;
import model.Question;
import service.ExamService;
import service.QuestionService;
import service.impl.ExamServiceImpl;
import service.impl.QuestionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/takeExam")
public class TakeExamServlet extends HttpServlet {
    private final ExamService examService = new ExamServiceImpl();
    private final QuestionService questionService = new QuestionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int examId = Integer.parseInt(request.getParameter("examId"));
        Exam exam = examService.getExamById(examId);

        // 验证考试时间
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(exam.getStartTime()) || now.isAfter(exam.getEndTime())) {
            request.setAttribute("errorMessage", "当前考试不可用，请检查考试时间！");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return;
        }

        List<Question> questions = questionService.getQuestionsByExamId(examId);
        request.setAttribute("exam", exam);
        request.setAttribute("questions", questions);
        request.getRequestDispatcher("/takeExam.jsp").forward(request, response);
    }
}
