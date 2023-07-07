package controller.auth;

import controller.DashboardController;
import dao.TeacherDAO;
import model.Teacher;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

  private final TeacherDAO teacherDao = new TeacherDAO();

  public static void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("teacher") == null) {
      req.setAttribute("log", "You have been logged out!");
      req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
  }

  public static void reload(HttpServletRequest req, HttpServletResponse resp) {
    Teacher t = (Teacher) req.getSession().getAttribute("teacher");
    String username = t.getUsername();
    String password = new TeacherDAO().getTeacherPassword(username);
    
    req.getSession().removeAttribute("teacher");
    req.getSession().setAttribute("teacher", new TeacherDAO().getTeacher(username, password));
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    System.out.println(username + " " + password);
    
    Teacher teacher = teacherDao.getTeacher(username, password);

    if (teacher != null) {
      HttpSession session = req.getSession();
      session.setAttribute("teacher", teacher);
      session.setMaxInactiveInterval(-1);
      resp.sendRedirect(req.getContextPath() + "/");
    } else {
      req.setAttribute("log", "Wrong username or password!");
      req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
  }
}
