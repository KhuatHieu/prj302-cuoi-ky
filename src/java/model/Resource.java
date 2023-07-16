package model;

import controller.DownloadController;
import utilities.File;

public class Resource {

  String id, name, path;

  public Resource() {
  }

  public Resource(String id, String name, String path) {
    this.id = id;
    this.name = name;
    this.path = path;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPath() {
    return path;
  }

  public String getDownloadKey() throws Exception {
    return new DownloadController().getFileName(path);
  }

  public String getExtension() {
    return File.getFileExtension(name);
  }

//  Google icons
  public String getExtensionIcon() {
    String extension = this.getExtension();
    switch (extension) {
      case "mp3":
        return "music_note";
      case "mp4":
        return "movie";
      case "zip":
      case "rar":
        return "folder_zip";
      case "doc":
      case "docx":
      case "txt":
        return "description";
      case "pdf":
        return "picture_as_pdf";
      case "xls":
      case "xlsx":
        return "pivot_table_chart";
      case "jpg":
      case "jpeg":
      case "png":
        return "image";
      default:
        return "question_mark";
    }
  }
}
