package dao;

import database.DBContext;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.*;

public class AnswerDAO extends DBContext {

//  return answerId
  public int createAnswer(int studentId, int testId) {
    try {
      String strQuery = "INSERT INTO dbo.Answer\n"
              + "VALUES (?, ?, -1)";
      PreparedStatement stm = connection.prepareStatement(strQuery,
              Statement.RETURN_GENERATED_KEYS);
      stm.setInt(1, studentId);
      stm.setInt(2, testId);

      stm.execute();
      ResultSet generatedKeys = stm.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public String getStudentName(int studentId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Student\n"
              + "WHERE StudentId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, studentId);

      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return rs.getString(2);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public ArrayList<Answer> getAnswerList() {
    ArrayList<Answer> answerList = new ArrayList<>();

    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Answer";
      PreparedStatement stm = connection.prepareStatement(strQuery);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        answerList.add(new Answer(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getFloat(4)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return answerList;
  }

  public ArrayList<Answer> getAnswerListByTestId(int testId) {
    ArrayList<Answer> answerList = new ArrayList<>();

    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Answer\n"
              + "WHERE TestId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, testId);
      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        answerList.add(new Answer(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getFloat(4)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return answerList;
  }

  public void gradeAnswer(int answerId, float mark) {
    try {
      String strQuery = "UPDATE dbo.Answer\n"
              + "SET Mark = ?\n"
              + "WHERE AnswerId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setFloat(1, mark);
      stm.setInt(2, answerId);
      
      stm.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
