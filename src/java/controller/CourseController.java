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
import java.util.ArrayList;

@WebServlet(name = "course", urlPatterns = {"/course"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)

public class CourseController extends HttpServlet {

  private final String dbPath = File.databasePath;

  private final CourseDAO courseDAO = new CourseDAO();
  private final ResourceDAO resourceDAO = new ResourceDAO();
  private final TestDAO testDAO = new TestDAO();

  private HttpSession session;

  private void showDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("resourceList",
            resourceDAO.getCourseResourceList(req.getParameter("courseId")));

    req.setAttribute("courseList",
            courseDAO.getCourseListByTeacherId(((Teacher) req.getSession().getAttribute("teacher")).getTeacherId()));

    req.setAttribute("courseId", req.getParameter("courseId"));
    req.setAttribute("course", courseDAO.getCourseByCourseId(
            req.getParameter("courseId")));

    req.setAttribute("classList", new ClassDAO().getClassList());

    ArrayList<Test> testList = new TestDAO().getTestListByCourseId(Integer.parseInt(req.getParameter("courseId")));
    testList.sort((t1, t2) -> {
//      On going -> Not graded -> Graded
      final String ORDER = "ONG";
      char t1Status = t1.getStatus().charAt(0);
      char t2Status = t2.getStatus().charAt(0);
      
      return ORDER.indexOf(t1Status) - ORDER.indexOf(t2Status);
    });

    req.setAttribute("testList", testList);

    if (req.getParameter("testId") != null) {
      int testId = Integer.parseInt(req.getParameter("testId"));
      req.setAttribute("testId", String.valueOf(testId));
      req.setAttribute("testName", testDAO.getTestByTestId(testId).getTestName());
      req.setAttribute("test",
              testDAO.getTestByTestId(Integer.parseInt(req.getParameter("testId"))));
      req.setAttribute("testResourceList", resourceDAO.getTestResourceList(testId));
    }

    req.getRequestDispatcher("course.jsp").forward(req, resp);
  }

  private void uploadCourseResource(HttpServletRequest req, String courseId) throws ServletException, IOException {
    new ResourceController().uploadCourseResource(req, courseId);
  }

  private void deleteCourseResource(String[] resourceIdList) {
    if (resourceIdList != null) {
      for (String resourceId : resourceIdList) {
        resourceDAO.deleteCourseResource(resourceId);
      }
    }
  }

  private void uploadTestResource(HttpServletRequest req, int testId) throws ServletException, IOException {
    System.out.println("UPDAEDDDDD");
    new ResourceController().uploadTestResource(req, testId);
  }

  private void deleteTestResource(String[] testIdList) {
    if (testIdList != null) {
      for (String testId : testIdList) {
        resourceDAO.deleteTestResource(Integer.parseInt(testId));
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

  private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    resp.sendRedirect("./");
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
      case "delete":
        deleteCourse(req, resp);
        break;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    switch (req.getParameter("action")) {
      case "updateCourseName":
        courseDAO.updateCourseName(req.getParameter("courseId"),
                req.getParameter("courseName"));
        break;
      case "updateCourseDesc":
        courseDAO.updateCourseDescription(req.getParameter("courseId"),
                req.getParameter("courseDesc"));
        break;
      case "uploadCourseResource":
        uploadCourseResource(req, req.getParameter("courseId"));
        break;
      case "deleteCourseResource":
        deleteCourseResource(req.getParameterValues("selectedResourceIds"));
        break;
      case "deleteTestResource":
        deleteTestResource(req.getParameterValues("selectedResourceIds"));
        break;
      case "uploadTestResource":
        uploadTestResource(req, Integer.parseInt(req.getParameter("testId")));
        break;
      case "createTest":
        new TestController().createTest(req);
        break;
      case "updateTest":
        new TestController().updateTest(req, Integer.parseInt(req.getParameter("testId")));
        break;
    }
    resp.sendRedirect("./course?" + req.getParameter("queryString"));
  }
}
