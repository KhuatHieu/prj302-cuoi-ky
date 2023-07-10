package dao;

import database.DBContext;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.Course;
import model.Test;

public class TestDAO extends DBContext {

  public void createTest(String testName, int courseId, int classId, Timestamp dueDate) {
    try {
      String strQuery = "INSERT INTO dbo.Test\n"
              + "VALUES (?, ?, ?, ?)";
      PreparedStatement stm = connection.prepareStatement(strQuery,
              Statement.RETURN_GENERATED_KEYS);
      stm.setString(1, testName);
      stm.setInt(2, courseId);
      stm.setInt(3, classId);
      stm.setTimestamp(4, dueDate);

      stm.execute();

      int testId = -1;
      ResultSet keys = stm.getGeneratedKeys();
      if (keys.next()) {
        testId = keys.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Test getTestByTestId(int testId) {
    try {
      String strQuery = "SELECT * FROM dbo.Test WHERE TestId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, testId);

      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return new Test(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getTimestamp(5)
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public ArrayList<Test> getTestListByCourseId(int id) {
    ArrayList<Test> courseList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Test WHERE CourseID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, id);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        courseList.add(new Test(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getTimestamp(5)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courseList;
  }
}
