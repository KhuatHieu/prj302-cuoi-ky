package model;

public class Answer {

  int studentId, testId;
  float mark;

  public Answer() {
  }

  public Answer(int studentId, int testId) {
    this.studentId = studentId;
    this.testId = testId;
    mark = -1;
  }

  public int getStudentId() {
    return studentId;
  }

  public int getTestId() {
    return testId;
  }

  public float getMark() {
    return mark;
  }
}
