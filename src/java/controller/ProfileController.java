package controller;

import controller.auth.LoginController;
import dao.TeacherDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProfileController extends HttpServlet {

  private final TeacherDAO teacherDAO = new TeacherDAO();
  
  private void changeProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String teacherId = req.getParameter("teacherId");
    String newFullName = req.getParameter("fullName");
    String newShortName = req.getParameter("shortName");
    
    teacherDAO.changeProfile(teacherId, newFullName, newShortName);
  }

  private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String teacherId = req.getParameter("teacherId");
    String newPassword = req.getParameter("newPassword");
    
    teacherDAO.changePassword(teacherId, newPassword);
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginController.checkLogin(req, resp);
    if (req.getAttribute("action") != null) {
      this.doPost(req, resp);
    }
    LoginController.reload(req, resp);
    req.getRequestDispatcher("profile.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    switch ((String) req.getParameter("action")) {
      case "changeProfile":
        this.changeProfile(req, resp);
        break;
      case "changePassword":
        this.changePassword(req, resp);
        break;
    }
    this.doGet(req, resp);
  }

}
