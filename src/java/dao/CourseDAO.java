package dao;

import database.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Course;

public class CourseDAO extends DBContext {

  public ArrayList<Course> getCourseList() {
    ArrayList<Course> courseList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Course";
      PreparedStatement stm = connection.prepareStatement(strQuery);

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

  public Course getCourseByCourseId(String courseId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Course\n"
              + "WHERE CourseID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, courseId);

      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return new Course(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5)
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public ArrayList<Course> getCourseListByTeacherId(int id) {
    ArrayList<Course> courseList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Course WHERE TeacherID = ?";
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

  public void createCourse(String courseName, String description, int subjectId, int teacherId) {
    try {
      String strQuery = "INSERT INTO dbo.Course VALUES (?, ?, ?, ?)";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, courseName);
      stm.setString(2, description);
      stm.setInt(3, subjectId);
      stm.setInt(4, teacherId);

      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void changeCourseName(String courseId, String courseName) {
    try {
      String strQuery = "UPDATE dbo.Course\n"
              + "SET CourseName = ?\n"
              + "WHERE CourseID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, courseId);
      stm.setString(2, courseName);

      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void changeCourseDescription(String courseId, String courseDesc) {
    try {
      String strQuery = "UPDATE dbo.Course\n"
              + "SET CourseDescription = ?\n"
              + "WHERE CourseID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, courseId);
      stm.setString(2, courseDesc);

      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
