package model;

import dao.ClassDAO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Test {

//  hardcoded in database
//  PLEASE dont touch
  public static final String ONGOING = "Ongoing", NOT_GRADED = "Not graded",
          GRADED = "Graded";

  int testId;
  String testName;
  int courseId, classId;
  Timestamp date;
  String status = ONGOING;

  public Test() {
  }

  public Test(int testId, String testName, int courseId, int classId, Timestamp date, String status) {
    this.testId = testId;
    this.testName = testName;
    this.courseId = courseId;
    this.classId = classId;
    this.date = date;
    this.status = status;
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
    return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
  }

  public String getStatus() {
    return status;
  }

}
