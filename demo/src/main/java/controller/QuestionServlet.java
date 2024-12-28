package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Question;
import service.QuestionService;
import service.impl.QuestionServiceImpl;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    private final QuestionService questionService = new QuestionServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    handleCreateQuestion(request, response);
                    break;
                case "update":
                    handleUpdateQuestion(request, response);
                    break;
                case "delete":
                    handleDeleteQuestion(request, response);
                    break;
                default:
                    sendErrorPage(request, response, "未知的操作。");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorPage(request, response, "服务器内部错误，请稍后再试。");
        }
    }

    private void handleCreateQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionText = request.getParameter("questionText");
        String correctAnswer = request.getParameter("correctAnswer");
        String type = request.getParameter("type");
        String difficulty = request.getParameter("difficulty");
        int score = Integer.parseInt(request.getParameter("score"));

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setCorrectAnswer(correctAnswer);
        question.setType(type);
        question.setDifficulty(difficulty);
        question.setScore(score);

        boolean success = questionService.createQuestion(question);
        if (success) {
            response.sendRedirect("manageQuestions.jsp");
        } else {
            sendErrorPage(request, response, "创建题目失败，请稍后重试。");
        }
    }

    private void handleUpdateQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String questionText = request.getParameter("questionText");
        String correctAnswer = request.getParameter("correctAnswer");
        String type = request.getParameter("type");
        String difficulty = request.getParameter("difficulty");
        int score = Integer.parseInt(request.getParameter("score"));

        Question question = new Question();
        question.setId(id);
        question.setQuestionText(questionText);
        question.setCorrectAnswer(correctAnswer);
        question.setType(type);
        question.setDifficulty(difficulty);
        question.setScore(score);

        boolean success = questionService.updateQuestion(question);
        if (success) {
            response.sendRedirect("manageQuestions.jsp");
        } else {
            sendErrorPage(request, response, "更新题目失败，请稍后重试。");
        }
    }

    private void handleDeleteQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = questionService.deleteQuestion(id);

        if (success) {
            response.sendRedirect("manageQuestions.jsp");
        } else {
            sendErrorPage(request, response, "删除题目失败，请稍后重试。");
        }
    }

    private void sendErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
        dispatcher.forward(request, response);
    }
}
