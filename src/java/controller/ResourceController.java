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

  public void uploadCourseResource(HttpServletRequest req, String courseId) throws ServletException, IOException {
    for (Part filePart : (ArrayList<Part>) req.getParts()) {
      if (filePart.getName().equals("file") && filePart.getSize() > 0) {
        String fileName = "";
        do {
          fileName = File.getUniqueFileName(filePart);
        } while (new java.io.File(dbPath + fileName).isFile());

        resourceDAO.uploadCourseResource(courseId, filePart.getSubmittedFileName(), dbPath + fileName);

        filePart.write(dbPath + fileName);
      }
    }
  }

  public void uploadTestResource(HttpServletRequest req, int testId) throws ServletException, IOException {
    for (Part filePart : (ArrayList<Part>) req.getParts()) {
      if (filePart.getName().equals("file") && filePart.getSize() > 0) {
        String fileName = "";
        do {
          fileName = File.getUniqueFileName(filePart);
        } while (new java.io.File(dbPath + fileName).isFile());

        resourceDAO.uploadTestResource(testId, filePart.getSubmittedFileName(), dbPath + fileName);

        filePart.write(dbPath + fileName);
      }
    }
  }
}
