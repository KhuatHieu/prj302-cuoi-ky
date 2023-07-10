package model;

public class Class {

  int classId;
  String className;

  public Class() {
  }

  public Class(int classId, String className) {
    this.classId = classId;
    this.className = className;
  }

  public int getClassId() {
    return classId;
  }

  public String getClassName() {
    return className;
  }

}
