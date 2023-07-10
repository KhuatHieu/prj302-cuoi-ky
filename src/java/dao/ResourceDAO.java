package dao;

import database.DBContext;
import model.Resource;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.File;

public class ResourceDAO extends DBContext {

//  upload a Resource for a Course
  public void uploadCourseResource(String courseId, String name, String path) {
    try {
      String iiResource = "INSERT INTO dbo.Resource\n"
              + "VALUES (?, ?)\n";
      PreparedStatement stm1 = connection.prepareStatement(iiResource,
              Statement.RETURN_GENERATED_KEYS);
      stm1.setString(1, name);
      stm1.setString(2, path);

      stm1.executeUpdate();
      ResultSet generatedKeys = stm1.getGeneratedKeys();
      String resourceId = "";
      if (generatedKeys.next()) {
        resourceId = Integer.toString(generatedKeys.getInt(1));
      }

      String iiCourseResource = "INSERT INTO dbo.CourseResource\n"
              + "VALUES (?, ?)";
      PreparedStatement stm2 = connection.prepareStatement(iiCourseResource);
      stm2.setString(1, courseId);
      stm2.setString(2, resourceId);

      stm2.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

//  upload a Resource for a Test
  public void uploadTestResource(int testId, String name, String path) {
    try {
      String iiResource = "INSERT INTO dbo.Resource\n"
              + "VALUES (?, ?)\n";
      PreparedStatement stm1 = connection.prepareStatement(iiResource,
              Statement.RETURN_GENERATED_KEYS);
      stm1.setString(1, name);
      stm1.setString(2, path);

      stm1.executeUpdate();
      ResultSet generatedKeys = stm1.getGeneratedKeys();
      String resourceId = "";
      if (generatedKeys.next()) {
        resourceId = Integer.toString(generatedKeys.getInt(1));
      }

      String iiCourseResource = "INSERT INTO dbo.TestResource\n"
              + "VALUES (?, ?)";
      PreparedStatement stm2 = connection.prepareStatement(iiCourseResource);
      stm2.setInt(1, testId);
      stm2.setString(2, resourceId);

      stm2.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public ArrayList<Resource> getResourceList(String courseId) {
    ArrayList<Resource> resourceList = new ArrayList<>();

    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.CourseResource cr\n"
              + "INNER JOIN dbo.Resource r\n"
              + "ON r.ResourceId = cr.ResourceId\n"
              + "WHERE cr.CourseId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, courseId);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        resourceList.add(new Resource(
                rs.getString(3),
                rs.getString(4),
                rs.getString(5)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resourceList;
  }
  
  public void deleteResource(String resourceId) {
    try {
//      Get filePath from resourceId
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, resourceId);

//      Delete the filePath
//      Should use sql transaction
//      delete record first then physical file
      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        new File(rs.getString(3)).delete();
      }

//      Delete record in dbo.CourseResource
//      why not DELETE CASCADE ?
      String dfResource = "DELETE FROM	dbo.CourseResource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm1 = connection.prepareStatement(dfResource);
      stm1.setString(1, resourceId);
      stm1.executeUpdate();

//      Delete record in dbo.Resource
      String dfCourseResource = "DELETE FROM dbo.Resource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm2 = connection.prepareStatement(dfCourseResource);
      stm2.setString(1, resourceId);
      stm2.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
