package model;

public class Teacher {

  int teacherId;
  String username, password, fullName, shortName;

  public Teacher() {
  }

  public Teacher(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Teacher(int teacherId, String username, String password, String fullName, String shortName) {
    this.teacherId = teacherId;
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.shortName = shortName;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getFullName() {
    return fullName;
  }

  public String getShortName() {
    return shortName;
  }

}
