package model;

public class Subject {

  int subjectId;
  String subjectName, subjectDetails;

  public Subject() {
  }

  public Subject(int subjectId, String subjectName, String subjectDetails) {
    this.subjectId = subjectId;
    this.subjectName = subjectName;
    this.subjectDetails = subjectDetails;
  }

  public int getSubjectId() {
    return subjectId;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public String getSubjectDetails() {
    return subjectDetails;
  }

}
