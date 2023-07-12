package controller;

import controller.auth.LoginController;
import dao.CourseDAO;
import dao.TestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
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
    
    ArrayList<Test> _testList = new TestDAO().getTestListByTeacherId(teacherId);
    
    int month = Integer.valueOf(req.getParameter("month"));
    int year = Integer.valueOf(req.getParameter("year"));
    
    Calendar c = Calendar.getInstance();
    c.set(Calendar.MONTH, month - 1);
    c.set(Calendar.YEAR, year);
    c.set(Calendar.DAY_OF_MONTH, 1);
    
    int startDayOfMonth = c.getTime().getDay();
    int endDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

//    only get Test of current month
    ArrayList<Test> testList = new ArrayList<>();
    for (Test test : _testList) {
      System.out.println(test.getDateTimestamp().getYear());
      if (test.getMonth() == month && test.getYear() == year) {
        testList.add(test);
      }
    }
    
    req.setAttribute("startDayOfMonth", startDayOfMonth);
    req.setAttribute("endDayOfMonth", endDayOfMonth);
    req.setAttribute("testList", testList);

    req.getRequestDispatcher("calendar.jsp").forward(req, resp);
  }

}
