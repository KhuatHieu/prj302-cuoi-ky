package dao;

import database.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDAO extends DBContext {

  public ArrayList<model.Class> getClassList() {
    ArrayList<model.Class> classList = new ArrayList<>();
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Class\n";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        classList.add(new model.Class(
                rs.getInt(1),
                rs.getString(2)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return classList;
  }

  public String getClassNameByClassId(int classId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Class\n"
              + "WHERE ClassId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, classId);
      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return rs.getString(2);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public int getClassIdByClassName(String className) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Class\n"
              + "WHERE ClassName = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, className);
      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }
}
