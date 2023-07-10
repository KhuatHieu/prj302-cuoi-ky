package controller;

import dao.ResourceDAO;
import dao.TestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class TestController {

  private final TestDAO testDAO = new TestDAO();

//  create a Test 
//  add TestResource records
  public void createTest(HttpServletRequest req) throws ServletException, IOException {
    String testName = req.getParameter("testName");
    int courseId = Integer.parseInt(req.getParameter("courseId"));
    int classId = Integer.parseInt(req.getParameter("classId"));

    Calendar _c = Calendar.getInstance();
    _c.set(Integer.parseInt(req.getParameter("year")),
            Integer.parseInt(req.getParameter("month")) - 1,
            Integer.parseInt(req.getParameter("day")),
            Integer.parseInt(req.getParameter("hour")),
            Integer.parseInt(req.getParameter("minute")),
            0
    );

    Timestamp dueDate = new Timestamp(_c.getTimeInMillis());

    testDAO.createTest(testName, courseId, classId, dueDate);

    new ResourceController().uploadTestResource(req, classId);
  }
}
