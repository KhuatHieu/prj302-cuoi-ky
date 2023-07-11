package controller;

import controller.auth.LoginController;
import dao.CourseDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Course;
import model.Teacher;
import model.Test;

public class DashboardController extends HttpServlet {

  private final SubjectDAO subjectDAO = new SubjectDAO();

  private final CourseDAO courseDAO = new CourseDAO();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginController.checkLogin(req, resp);
    req.setAttribute("subjectList", subjectDAO.getSubjectList());

    int teacherId = ((Teacher) req.getSession().getAttribute("teacher")).getTeacherId();
    req.setAttribute("courseList", courseDAO.getCourseListByTeacherId(teacherId));

    req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginController.checkLogin(req, resp);
    req.setAttribute("subjectList", subjectDAO.getSubjectList());

    int teacherId = ((Teacher) req.getSession().getAttribute("teacher")).getTeacherId();
    ArrayList<Course> courseList = courseDAO.getCourseListByTeacherId(teacherId);
    
    String searchStr = req.getParameter("searchStr");
    System.out.println(searchStr);
    ArrayList<Course> out = new ArrayList<>();
    
    for (Course c : courseList) {
      if (c.getCourseName().toLowerCase().contains(searchStr) 
              || c.getCourseDescription().toLowerCase().contains(searchStr)) {
        out.add(c);
      }
    }
    
    req.setAttribute("searchStr", searchStr);
    req.setAttribute("courseList", courseList);
    
    req.setAttribute("search", !searchStr.isEmpty());
    req.setAttribute("searchCourseList", out);
    
    req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
  }
  
  
}
