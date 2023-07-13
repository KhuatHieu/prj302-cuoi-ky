package controller;

import controller.auth.LoginController;
import dao.CourseDAO;
import dao.TestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import model.Teacher;
import model.Test;

public class CalendarController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginController.checkLogin(req, resp);
    Teacher teacher = (Teacher) req.getSession().getAttribute("teacher");
    int teacherId = teacher.getTeacherId();

    req.setAttribute("courseList", new CourseDAO().getCourseListByTeacherId(teacherId));

    int month = Integer.valueOf(req.getParameter("month"));
    int year = Integer.valueOf(req.getParameter("year"));

    req.setAttribute("month", month);
    req.setAttribute("year", year);

//    Set this Calendar c to today Date
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DAY_OF_MONTH, 1);
    c.set(Calendar.MONTH, month - 1);
    c.set(Calendar.YEAR, year);

    req.setAttribute("startDayOfMonth", c.getTime().getDay());
    req.setAttribute("endDayOfMonth", c.getActualMaximum(Calendar.DAY_OF_MONTH));

//    only get Test of current month
    ArrayList<Test> _testList = new TestDAO().getTestListByTeacherId(teacherId);
    ArrayList<Test> testList = new ArrayList<>();
    for (Test test : _testList) {
      if (test.getMonth() == month && test.getYear() == year) {
        testList.add(test);
      }
    }
    req.setAttribute("testList", testList);
    
    int notGradedCount = 0, ongoingCount = 0, gradedCount = 0;
    for (Test test : testList) {
      switch (test.getStatus()) {
        case Test.NOT_GRADED:
          notGradedCount++;
          break;
        case Test.ONGOING:
          ongoingCount++;
          break;
        case Test.GRADED:
          gradedCount++;
          break;
      }
    }
    req.setAttribute("notGradedCount", notGradedCount);
    req.setAttribute("ongoingCount", ongoingCount);
    req.setAttribute("gradedCount", gradedCount);

    req.setAttribute("isToday",
            month == Calendar.getInstance().get(Calendar.MONTH) + 1
            && year == Calendar.getInstance().get(Calendar.YEAR));

    req.getRequestDispatcher("calendar.jsp").forward(req, resp);
  }

}
