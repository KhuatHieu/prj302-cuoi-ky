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

  public void uploadAnswerResource(int answerId, String name, String path) {
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

      String iiCourseResource = "INSERT INTO dbo.AnswerResource\n"
              + "VALUES (?, ?)";
      PreparedStatement stm2 = connection.prepareStatement(iiCourseResource);
      stm2.setInt(1, answerId);
      stm2.setString(2, resourceId);

      stm2.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Resource getResourceByAnswerId(int answerId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource r\n"
              + "INNER JOIN dbo.AnswerResource ar\n"
              + "ON ar.ResourceId = r.ResourceId\n"
              + "WHERE ar.AnswerId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, answerId);

      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return this.getResourceByResourceId(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Resource getResourceByResourceId(int resourceId) {
    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource\n"
              + "WHERE ResourceId =  ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, resourceId);

      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        return new Resource(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3)
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public ArrayList<Resource> getCourseResourceList(String courseId) {
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

  public ArrayList<Resource> getTestResourceList(int testId) {
    ArrayList<Resource> resourceList = new ArrayList<>();

    try {
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource r\n"
              + "INNER JOIN dbo.TestResource tr\n"
              + "ON tr.ResourceId = r.ResourceId\n"
              + "WHERE tr.TestId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, testId);

      ResultSet rs = stm.executeQuery();
      while (rs.next()) {
        resourceList.add(new Resource(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3)
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resourceList;
  }

//  delete in both DB and physical drive
  public void deleteCourseResource(String resourceId) {
    try {
//      Get filePath from resourceId
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setString(1, resourceId);

//      Delete the filePath
//      TODO: sql transaction
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

  public void deleteTestResource(int testId) {
    try {
//      Get filePath from resourceId
      String strQuery = "SELECT * \n"
              + "FROM dbo.Resource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm = connection.prepareStatement(strQuery);
      stm.setInt(1, testId);

//      Delete the filePath
//      TODO: sql transaction
      ResultSet rs = stm.executeQuery();
      if (rs.next()) {
        new File(rs.getString(3)).delete();
      }

//      Delete record in dbo.CourseResource
//      why not DELETE CASCADE ?
      String dfResource = "DELETE FROM	dbo.TestResource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm1 = connection.prepareStatement(dfResource);
      stm1.setInt(1, testId);
      stm1.executeUpdate();

//      Delete record in dbo.Resource
      String dfCourseResource = "DELETE FROM dbo.Resource\n"
              + "WHERE ResourceId = ?";
      PreparedStatement stm2 = connection.prepareStatement(dfCourseResource);
      stm2.setInt(1, testId);
      stm2.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
