package utilities;

import jakarta.servlet.http.Part;
import java.util.Random;
import java.util.UUID;

public class File {

//  public static ArrayList<String> acceptedFileExtensions = new ArrayList<>(
//          Arrays.asList("doc", "docx", "rar", "zip")
//  );
  public static String databasePath = "D:\\prj302-cuoi-ky\\database\\";

  public static String getUniqueFileName(String decFileName) {
    String encId = "";
    for (int i = 0; i < 8; i++) {
      encId = encId + UUID.randomUUID().toString()
              .replaceAll("-", "")
              .charAt(new Random().nextInt(8));
    }

    return File.getFileName(decFileName)
            + "-" + encId
            + "." + File.getFileExtension(decFileName);
  }

  public static String getUniqueFileName(Part part) {
    String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    String encId = "";
    for (int i = 0; i < 8; i++) {
      encId = encId + characters.charAt(
              new Random().nextInt(characters.length()));
    }

    return File.getFileName(part.getSubmittedFileName())
            + "-" + encId
            + "." + File.getFileExtension(part.getSubmittedFileName());
  }

  public static String getDecryptedFileName(String encFileName) {
    return File.getFileName(encFileName)
            + "." + File.getFileExtension(encFileName);
  }

  public static String getFileName(String fileName) {
    int lastIndex = fileName.lastIndexOf(".");
    if (lastIndex != -1) {
      return fileName.substring(0, lastIndex);
    }
    return fileName;
  }

  public static String getFileExtension(String fileName) {
    int lastIndex = fileName.lastIndexOf(".");
    if (lastIndex != -1) {
      return fileName.substring(lastIndex + 1);
    }
    return "";
  }
}
