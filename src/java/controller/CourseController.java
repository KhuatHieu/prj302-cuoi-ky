package controller;

import dao.*;
import utilities.*;
import model.*;
import controller.auth.LoginController;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "course", urlPatterns = {"/course"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 25, // 25MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)

public class CourseController extends HttpServlet {

  private final String dbPath = File.databasePath;

  private final CourseDAO courseDAO = new CourseDAO();
  private final ResourceDAO resourceDAO = new ResourceDAO();

  private HttpSession session;

  private void showDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("resourceList",
            resourceDAO.getResourceList(req.getParameter("courseId")));
    req.setAttribute("courseList",
            courseDAO.getCourseListByTeacherId(((Teacher) req.getSession().getAttribute("teacher")).getTeacherId()));
    req.setAttribute("courseId", req.getParameter("courseId"));
    req.setAttribute("course", courseDAO.getCourseByCourseId(
            req.getParameter("courseId")));
    req.getRequestDispatcher("course.jsp").forward(req, resp);
  }

  private void uploadResource(HttpServletRequest req, String courseId) throws ServletException, IOException {
    new ResourceController().uploadResource(req, courseId);
  }

  private void deleteResource(String[] resourceIdList) {
    if (resourceIdList != null) {
      for (String resourceId : resourceIdList) {
        resourceDAO.deleteResource(resourceId);
      }
    }
  }

  private void addCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int subjectId = Integer.parseInt(req.getParameter("subjectId"));
    String subjectName = req.getParameter("subjectName");
    String term = req.getParameter("term");
    String description = req.getParameter("description");
    int teacherId = Integer.parseInt(req.getParameter("teacherId"));
    String tShortName = req.getParameter("tShortName");

    courseDAO.createCourse(subjectName + " - " + term + " - " + tShortName,
            description.isEmpty() ? "No descriptions" : description,
            subjectId, teacherId);
    resp.sendRedirect("./");
  }

  private void updateCourse(HttpServletRequest req, HttpServletResponse resp) {

  }

  private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginController.checkLogin(req, resp);
    session = req.getSession(false);
    switch (req.getParameter("action")) {
      case "details":
        showDetails(req, resp);
        break;
      case "add":
        addCourse(req, resp);
        break;
      case "update":
        updateCourse(req, resp);
        break;
      case "delete":
        deleteCourse(req, resp);
        break;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    switch (req.getParameter("action")) {
      case "uploadResource":
        uploadResource(req, req.getParameter("courseId"));
        resp.sendRedirect("./course" + "?" + req.getParameter("queryString"));
        break;
      case "deleteResource":
        deleteResource(req.getParameterValues("selectedResourceIds"));
        resp.sendRedirect("./course" + "?" + req.getParameter("queryString"));
        break;
    }
  }
}
