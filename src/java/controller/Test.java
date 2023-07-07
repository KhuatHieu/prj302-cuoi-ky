package controller;

import model.Teacher;
import dao.TeacherDAO;

public class Test {
  
  public static void main(String[] args) {
    String username = "khuathieu";
    String password = "khuathieu";
    
    TeacherDAO userDAO = new TeacherDAO();
    
    Teacher user = userDAO.getTeacher(username, password);
    if (user == null) {
      System.out.println("Not found any");
      return;
    }
  }
}
