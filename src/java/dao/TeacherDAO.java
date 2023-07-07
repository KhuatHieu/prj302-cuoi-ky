package dao;

import database.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Teacher;

public class TeacherDAO extends DBContext {

  String strQuery;
  PreparedStatement stm;
  ResultSet rs;

  public TeacherDAO() {
  }

  public Teacher getTeacher(String username, String password) {
    try {
      strQuery = "SELECT * FROM dbo.Teacher WHERE Username = ? AND Password = ?";
      stm = connection.prepareStatement(strQuery);
      stm.setString(1, username);
      stm.setString(2, password);

      rs = stm.executeQuery();
      while (rs.next()) {
        if (rs.getString(2).equals(username)) {
          if (rs.getString(3).equals(password)) {
            int rxTeacherId = rs.getInt(1);
            String rxUsername = rs.getString(2);
            String rxPassword = rs.getString(3);
            String rxFullName = rs.getString(4);
            String rxShortName = rs.getString(5);

            return new Teacher(rxTeacherId, rxUsername,
                    rxPassword, rxFullName, rxShortName);
          }
        } else {
          break;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getTeacherPassword(String username) {
    try {
      strQuery = "SELECT * \n"
              + "FROM dbo.Teacher\n"
              + "WHERE Username = ?";
      stm = connection.prepareStatement(strQuery);
      stm.setString(1, username);

      rs = stm.executeQuery();
      if (rs.next()) {
        return rs.getString(3);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void changeProfile(String teacherId, String newFullName, String newShortName) {
    try {
      strQuery = "UPDATE dbo.Teacher\n"
              + "SET FullName = ?, ShortName = ?\n"
              + "WHERE TeacherID = ?";
      stm = connection.prepareStatement(strQuery);
      stm.setString(1, newFullName);
      stm.setString(2, newShortName);
      stm.setInt(3, Integer.parseInt(teacherId));

      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void changePassword(String teacherId, String newPassword) {
    try {
      strQuery = "UPDATE dbo.Teacher\n"
              + "SET Password = ? WHERE TeacherID = ?";
      stm = connection.prepareStatement(strQuery);

      System.out.println(newPassword + " " + teacherId);

      stm.setString(1, newPassword);
      stm.setString(2, teacherId);

      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
