package controller.auth;

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

  private static String nextUrl = "";
  
  public static boolean isLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null || session.getAttribute("teacher") == null) {
      nextUrl = req.getRequestURI() + "?"+ req.getQueryString();
      if (nextUrl.isEmpty()) {
        resp.sendRedirect("./login");
      } else {
        resp.sendRedirect("./login?have-continue");
      }
      return false;
      
//      req.setAttribute("log", "Session expired! Please re-login");
//      req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
    return true;
  }

  public static void reloadSession(HttpServletRequest req, HttpServletResponse resp) {
    Teacher t = (Teacher) req.getSession().getAttribute("teacher");
    String username = t.getUsername();
    String password = new TeacherDAO().getTeacherPassword(username);

    req.getSession().removeAttribute("teacher");
    req.getSession().setAttribute("teacher", new TeacherDAO().getTeacher(username, password));
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!nextUrl.isEmpty()) {
      req.setAttribute("log", "Please login to continue.");
    }
    req.getRequestDispatcher("login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    Teacher teacher = teacherDao.getTeacher(username, password);

    if (teacher != null) {
      HttpSession session = req.getSession();
      System.out.println(req.getParameter("nextUrl"));
      session.setAttribute("teacher", teacher);
      if (nextUrl.isEmpty()) {
        resp.sendRedirect("./");
      } else {
        resp.sendRedirect(nextUrl);
        nextUrl = "";
      }
      
    } else {
      req.setAttribute("log", "Wrong username or password!");
      req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
  }
}
