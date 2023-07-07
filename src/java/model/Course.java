package model;

import java.util.ArrayList;

public class Course {

  int id;
  String courseName, courseDescription;
  int subjectId, teacherId;
  ArrayList<Test> testList = new ArrayList<>();
  
  public Course() {
  }

  public Course(int id, String courseName, String courseDescription, int subjectId, int teacherId) {
    this.id = id;
    this.courseName = courseName;
    this.courseDescription = courseDescription;
    this.subjectId = subjectId;
    this.teacherId = teacherId;
  }

  public int getId() {
    return id;
  }

  public String getCourseName() {
    return courseName;
  }

  public String getCourseDescription() {
    return courseDescription;
  }
  
  public int getSubjectId() {
    return subjectId;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public ArrayList<Test> getTestList() {
    return testList;
  }
}
