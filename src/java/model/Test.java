package model;

import dao.ClassDAO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

//  hardcoded
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

  public Timestamp getDateTimestamp() {
    return date;
  }

  public int getMonth() {
    return date.getMonth() + 1;
  }

  public int getYear() {
    return date.getYear() + 1900;
  }

  public int getDayOfMonth() {
    return date.toLocalDateTime().getDayOfMonth();
  }

  public boolean isPassedDueDate() {
    Date current = new Date();
    Date timestampDate = new Date(date.getTime());
    
    return current.after(timestampDate);
  }
  
  public String getStatus() {
    if (isPassedDueDate()) {
      if (status.equals(ONGOING)) {
        status = NOT_GRADED;
      }
    }
    return status;
  }

//  get Bootstrap badge bgs
//  for front-end
//  possibile values: bg-warning, bg-success, bg-danger
  public String getBadgeBg() {
    switch (getStatus()) {
      case ONGOING:
        return "bg-warning";
      case NOT_GRADED:
        return "bg-danger";
      case GRADED:
        return "bg-success";
    }
    return "";
  }
}
