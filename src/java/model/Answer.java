package model;

import dao.AnswerDAO;
import dao.ResourceDAO;

public class Answer {

  int answerId;
  int studentId, testId;
  float mark;

  public Answer() {
  }

  public Answer(int answerId, int studentId, int testId, float mark) {
    this.answerId = answerId;
    this.studentId = studentId;
    this.testId = testId;
    this.mark = mark;
  }

  public int getAnswerId() {
    return answerId;
  }

  public int getStudentId() {
    return studentId;
  }

  public String getStudentName() {
    return new AnswerDAO().getStudentName(studentId);
  }

  public int getTestId() {
    return testId;
  }

  public float getMark() {
    return mark;
  }
  
  public String getResourceName() {
    return new ResourceDAO().getResourceByAnswerId(answerId).getName();
  }
  
  public String getDownloadKey() throws Exception {
    return new ResourceDAO().getResourceByAnswerId(answerId).getDownloadKey();
  }
}
