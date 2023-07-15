package dao;

import database.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Subject;

public class SubjectDAO extends DBContext {

  public ArrayList<Subject> getSubjectList() {
    ArrayList<Subject> subjectList = new ArrayList<>();

    try {
      String strQuery = "SELECT * FROM dbo.Subject ORDER BY SubjectName ASC";
      PreparedStatement stm = connection.prepareStatement(strQuery);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        subjectList.add(new Subject(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return subjectList;
  }

  public String getSubjectDetails(int subjectId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Subject\n"
              + "WHERE SubjectID = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, subjectId);
      
      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return rs.getString(3);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return "";
  }
}
