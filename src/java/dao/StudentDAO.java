package dao;

import database.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;

public class StudentDAO extends DBContext {

  public ArrayList<Student> getStudentList() {
    ArrayList<Student> studentList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Student";
      PreparedStatement stm = connection.prepareStatement(strQuery);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        studentList.add( new Student(
                rs.getInt(1),
                rs.getString(2)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return studentList;
  }
}
