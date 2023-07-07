package dao;

import database.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Course;

public class TestDAO extends DBContext {

  public ArrayList<Course> getTestListByCourseId(int id) {
    ArrayList<Course> courseList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Test WHERE CourseID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, id);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        courseList.add(new Course(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courseList;
  }
}
