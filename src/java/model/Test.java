package model;

import java.sql.Timestamp;

public class Test {

  public static final String IN_PROGRESS = "In progress", NOT_REVIEWED = "Done - Not reviewed",
          REVIEWED = "Done - Reviewed";

  int testId;
  String testName;
  Timestamp date;
  String status = IN_PROGRESS;

  public Test() {
  }

  public Test(String testName, Timestamp date) {
    this.testName = testName;
    this.date = date;
  }

  public int getTestId() {
    return testId;
  }

  public String getTestName() {
    return testName;
  }

  public Timestamp getDate() {
    return date;
  }

  public String getStatus() {
    return status;
  }

}
