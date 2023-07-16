package controller;

import controller.auth.LoginController;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class DownloadController extends HttpServlet {

  public String getFileName(String key) {
    return key.substring(key.lastIndexOf("\\") + 1);
  }

  public String getFilePath(String encKey) {
    return utilities.File.databasePath + "\\" + encKey;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!LoginController.isLogin(req, resp)) {
      return;
    }
    String downloadKey = this.getFilePath(req.getParameter("downloadKey"));
        
    File file = new File(downloadKey);
    FileInputStream inputStream = new FileInputStream(file);

    // set response headers
    resp.setContentType("application/octet-stream");
    resp.setContentLength((int) file.length());
    resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

    // write file to response outputstream
    OutputStream outputStream = resp.getOutputStream();
    byte[] buffer = new byte[4096];
    int bytesRead = -1;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }
    inputStream.close();
    outputStream.close();
  }

}
