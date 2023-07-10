package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class DownloadController extends HttpServlet {

  public String encrypt(String key) {
    return key.substring(key.lastIndexOf("\\") + 1);
  }

  public String decrypt(String encKey) {
    return utilities.File.databasePath + "\\" + encKey;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String downloadKey = this.decrypt(request.getParameter("downloadKey"));

    File file = new File(downloadKey);
    FileInputStream inputStream = new FileInputStream(file);

    // set response headers
    response.setContentType("application/octet-stream");
    response.setContentLength((int) file.length());
    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

    // write file to response output stream
    OutputStream outputStream = response.getOutputStream();
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }
    inputStream.close();
    outputStream.close();
  }

}
