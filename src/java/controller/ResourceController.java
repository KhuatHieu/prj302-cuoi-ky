package controller;

import dao.ResourceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import utilities.File;

public class ResourceController extends HttpServlet {

  private final String dbPath = File.databasePath;

  private final ResourceDAO resourceDAO = new ResourceDAO();

  public void uploadResource(HttpServletRequest req, String courseId) throws ServletException, IOException {
    ArrayList<Part> fileParts = (ArrayList<Part>) req.getParts();
    for (Part filePart : fileParts) {
      if (filePart.getName().equals("file")) {
        String fileName = "";
        do {
          fileName = File.getUniqueFileName(filePart);
        } while (new java.io.File(dbPath + fileName).isFile());

//    Create a record in dbo.Resource and dbo.CourseResource
        resourceDAO.createResourceRecord(courseId, filePart.getSubmittedFileName(), dbPath + fileName);

//    Upload file to server
        filePart.write(dbPath + fileName);
      }
    }
  }

  public void deleteResource(HttpServletRequest req, ArrayList<String> resourceIdList) {
    for (String resId : resourceIdList) {
//      resourceDAO.deleteResource(resId);
      System.out.println(resId);
    }
  }
}
