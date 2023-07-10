package model;

import dao.ClassDAO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Test {

  public static final String IN_PROGRESS = "In progress", NOT_REVIEWED = "Done - Not reviewed",
          REVIEWED = "Done - Reviewed";

  int testId;
  String testName;
  int courseId, classId;
  Timestamp date;
  String status = IN_PROGRESS;

  public Test() {
  }

  public Test(int testId, String testName, int courseId, int classId, Timestamp date) {
    this.testId = testId;
    this.testName = testName;
    this.courseId = courseId;
    this.classId = classId;
    this.date = date;
  }

  public int getTestId() {
    return testId;
  }

  public String getTestName() {
    return testName;
  }

  public int getCourseId() {
    return courseId;
  }

  public int getClassId() {
    return classId;
  }

  public String getClassName() {
    return new ClassDAO().getClassNameByClassId(classId);
  }
  
  public String getDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    return sdf.format(date);
  }

  public String getStatus() {
    return status;
  }

}
