package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Exam;
import model.Question;
import service.ExamService;
import service.impl.ExamServiceImpl;

@WebServlet("/exam")
public class ExamServlet extends HttpServlet {
    private final ExamService examService = new ExamServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    handleCreateExam(request, response);
                    break;
                case "addQuestionToExam":
                    handleAddQuestionToExam(request, response);
                    break;
                case "removeQuestionFromExam":
                    handleRemoveQuestionFromExam(request, response);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            handleListExams(request, response);
        } else {
            sendErrorPage(request, response, "未知的操作。");
        }
    }




    private void handleListExams(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Exam> exams = examService.getAllExams();
        request.setAttribute("examList", exams); // 设置考试列表到请求中
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listExams.jsp");
        dispatcher.forward(request, response);
    }


    private void handleCreateExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String durationStr = request.getParameter("duration");
            String description = request.getParameter("description");

            // 验证输入
            StringBuilder errorMessage = new StringBuilder();
            if (name == null || name.trim().isEmpty()) errorMessage.append("考试名称不能为空。<br>");
            if (startTimeStr == null || startTimeStr.trim().isEmpty()) errorMessage.append("开始时间不能为空。<br>");
            if (endTimeStr == null || endTimeStr.trim().isEmpty()) errorMessage.append("结束时间不能为空。<br>");
            if (durationStr == null || durationStr.trim().isEmpty()) errorMessage.append("持续时间不能为空。<br>");

            if (!errorMessage.isEmpty()) {
                request.setAttribute("errorMessage", errorMessage.toString());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/createExam.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // 解析时间和持续时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
            int duration = Integer.parseInt(durationStr);

            // 创建考试对象
            Exam exam = new Exam();
            exam.setName(name);
            exam.setStartTime(startTime);
            exam.setEndTime(endTime);
            exam.setDuration(duration);
            exam.setDescription(description);

            // 调用服务层
            boolean success = examService.createExam(exam);
            if (success) {
                response.sendRedirect("listExams.jsp");
            } else {
                sendErrorPage(request, response, "创建考试失败，请稍后重试。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorPage(request, response, "创建考试时发生错误，请检查输入数据。");
        }
    }

    private void handleAddQuestionToExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String examIdParam = request.getParameter("examId");
            String questionIdParam = request.getParameter("questionId");

            if (examIdParam == null || questionIdParam == null || examIdParam.trim().isEmpty() || questionIdParam.trim().isEmpty()) {
                sendErrorPage(request, response, "考试 ID 或试题 ID 缺失。");
                return;
            }

            int examId = Integer.parseInt(examIdParam);
            int questionId = Integer.parseInt(questionIdParam);

            boolean success = examService.addQuestionToExam(examId, questionId);
            if (success) {
                loadExamDetails(request, examId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/manageExam.jsp");
                dispatcher.forward(request, response);
            } else {
                sendErrorPage(request, response, "无法将试题添加到考试，请重试。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorPage(request, response, "添加试题到考试时发生错误。");
        }
    }

    private void handleRemoveQuestionFromExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String examIdParam = request.getParameter("examId");
            String questionIdParam = request.getParameter("questionId");

            if (examIdParam == null || questionIdParam == null || examIdParam.trim().isEmpty() || questionIdParam.trim().isEmpty()) {
                sendErrorPage(request, response, "考试 ID 或试题 ID 缺失。");
                return;
            }

            int examId = Integer.parseInt(examIdParam);
            int questionId = Integer.parseInt(questionIdParam);

            boolean success = examService.removeQuestionFromExam(examId, questionId);
            if (success) {
                loadExamDetails(request, examId);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/manageExam.jsp");
                dispatcher.forward(request, response);
            } else {
                sendErrorPage(request, response, "无法从考试中移除试题，请重试。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorPage(request, response, "从考试中移除试题时发生错误。");
        }
    }

    private void loadExamDetails(HttpServletRequest request, int examId) throws ServletException {
        try {
            Exam exam = examService.getExamById(examId);
            List<Question> questions = examService.getQuestionsByExamId(examId);
            request.setAttribute("exam", exam);
            request.setAttribute("examQuestions", questions);


            if (exam == null) {
                throw new ServletException("未找到考试 ID 为 " + examId + " 的考试");
            }

            request.setAttribute("exam", exam); // 设置考试对象到请求中
            request.setAttribute("examQuestions", questions); // 设置试题列表到请求中
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("加载考试详情时发生错误", e);
        }
    }

    private void handleViewExam(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examIdParam = request.getParameter("examId");
        if (examIdParam == null || examIdParam.isEmpty()) {
            request.setAttribute("errorMessage", "缺少考试 ID 参数。");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }

        int examId;
        try {
            examId = Integer.parseInt(examIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "无效的考试 ID 参数。");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }

        Exam exam = examService.getExamById(examId);
        if (exam == null) {
            request.setAttribute("errorMessage", "未找到考试 ID 为 " + examId + " 的考试。");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
            return;
        }

        List<Question> questions = examService.getQuestionsByExamId(examId);
        request.setAttribute("exam", exam);
        request.setAttribute("examQuestions", questions);

        request.getRequestDispatcher("manageExam.jsp").forward(request, response);
    }





    private void sendErrorPage(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/errorPage.jsp");
        dispatcher.forward(request, response);
    }
}
