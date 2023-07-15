<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>

  <script>
    function createCourse() {
      var s = document.getElementById('subject')
      var subjectId = s.options[s.selectedIndex].value
      var subjectName = s.options[s.selectedIndex].text

      var t = document.getElementById('term')
      var term = t.options[t.selectedIndex].text

      var description = document.getElementById('description').value
      var teacherId = document.getElementById('teacherId').value
      var tShortName = document.getElementById('tShortName').value

      var createCourseBtn = document.getElementById('createCourseBtn')

      window.location.href = './course?action=add&subjectId=' + subjectId + '&subjectName=' + subjectName
              + '&term=' + term + '&description=' + description
              + '&teacherId=' + teacherId + '&tShortName=' + tShortName
    }
  </script>
  <% 
    Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
    String searchStr = "";
    if (request.getAttribute("searchStr") != null) {
      searchStr = (String) request.getAttribute("searchStr");
    }
  %>
</head>

<body>
  <!-- Navbar -->
  <jsp:include page="navbar.jsp"/>
  
  <div class="row">

    <!-- sidebar -->
    <div class="col-2 min-vh-100 position-fixed" style="background-color: #212529;">
      <div class="p-3">
        <ul class="list-unstyled">
          <li class="mb-1">
            <a href='./' class="btn btn-dark w-100 ${search ? '' : 'active'}" style="color: white;">
              <div class="row">
                <div class="col-2">
                  <span class='${search ? "material-symbols-outlined" : "material-icons"}'>home</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>Home</h5>
                </div>
              </div>
            </a>
          </li>
          <li class="mb-1">
            <a href='./calendar' class="btn btn-dark w-100" style="color: white;" id='calendarHref'>
              <div class="row">
                <div class="col-2">
                  <span class='material-symbols-outlined'>calendar_month</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>Calendar</h5>
                </div>
              </div>
            </a>
          </li>
          <script>
            const month = new Date().getMonth() + 1
            const year = new Date().getFullYear()
            document.getElementById('calendarHref').href = './calendar?month=' + month + '&year=' + year
          </script>
          <li class="mb-1">
            <button class="btn btn-toggle btn-dark w-100" data-bs-toggle="collapse" data-bs-target="#dashboard-collapse" style="color: white;">
              <div class="row">
                <div class="col-2">
                  <span class='material-symbols-outlined'>bookmark</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>My courses</h5>
                </div>
              </div>
            </button>
            <div class="collapse show" id="dashboard-collapse">
              <ul class="list-unstyled overflow-y-auto" style="color: white; max-height: 60vh;">
                <c:forEach items="${courseList}" var="c">
                  <li>
                    <a href="./course?action=details&courseId=${c.id}" class="btn btn-dark align-items-center" 
                      style="font-size: medium; color: white; text-align: left;">
                      <div class="row align-items-center ms-0">
                        <div class="col-2">
                          <span class="material-symbols-outlined align-middle me-0">school</span>
                        </div>
                        <div class="col-10">
                          ${c.getCourseName()}
                        </div>
                      </div>
                    </a>
                  </li>
                </c:forEach>
              </ul>
            </div>
          </li>
          <li class="mb-1" style='position: absolute; bottom: 4rem;'>
            <hr class='border w-100'>
            <button class="btn btn-dark w-100" style="color: white;">
              <div class="row">
                <div class="col-2">
                  <span class='material-symbols-outlined'>account_circle</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>${teacher.getFullName()}</h5>
                </div>
              </div>
            </button>
          </li>
        </ul>
      </div>
    </div>

    <div class="col-9" style='margin-left: 16.667%;'>
      <!-- Search and add Course modal -->
      <div class="row ms-0">

        <!-- Search box -->
        <div class="col-6">
          <form action="" method="post">
            <div class="input-group mt-3">
              <input type="text" value='<%=searchStr%>' class="form-control border border-dark"  placeholder="Search [Alt + /] for your course" name='searchStr'
                  aria-label="Course" aria-describedby="basic-addon1" id="searchInput" accesskey='/' style='background-color: #F1F3F4;'>
              <button type='submit' class='btn btn-light btn-sm border border-dark'>
                <span class='material-icons align-middle' style='color: #1967D2'>search</span>
                <span class='fw-semibold' style='color: #1967D2; font-size: 1rem;'>Search</span>
              </button>
            </div>
          </form>
        </div>

        <!-- Modal -->
        <div class="col-2 mt-3">
          <button type='button' class='btn btn-success w-100' data-bs-toggle="modal" data-bs-target="#createCourse">
            Create new Course
          </button>
          <div class="modal fade" id="createCourse" data-bs-backdrop='static' tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="exampleModalLabel">Create new Course</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <label for="courseName" class='form-label'>Course name</label>
                  <input type="hidden" name="teacherId" id="teacherId" value='<%=teacher.getTeacherId()%>'>

                  <div class="input-group" id='courseName'>                     
                    <div class="input-group-text">
                      <input class="form-check-input mt-0" type="radio" name='courseName' value="formattedName" checked>
                    </div>
                    <select name='subject' id='subject' class="form-control selectpicker border" data-live-search="true"
                      style='overflow-y: auto; max-height: 200px;'>
                      <c:forEach items="${subjectList}" var="s">
                        <option value="${s.subjectId}">${s.subjectName}</option>
                      </c:forEach>
                    </select>
                    <span class="input-group-text">-</span>
                    <select name='term' id='term' class="form-control border selectpicker">
                      <option value="SU22">SU22</option>
                      <option value="FA22">FA22</option>
                      <option value="SP23">SP23</option>
                      <option value="SU23" selected>SU23</option>
                      <option value="FA23">FA23</option>
                    </select>
                    <span class="input-group-text">-</span>
                    <input name='tShortName' id='tShortName' type="text" class="form-control" placeholder="TShortName" aria-label="TShortName" value='<%=teacher.getShortName()%>'>
                  </div>
                  <div class="input-group mt-1">
                    <div class="input-group-text">
                      <input disabled class="form-check-input mt-0" type="radio" name='courseName' id='customCourseName' aria-label="Checkbox for following text input">
                    </div>
                    <input type="text" class="form-control" id='myCustomCourseName' aria-label="Text input with checkbox" 
                      placeholder='Use a custom name instead? // TODO' disabled>
                  </div>

                  <label for="description" class='form-label mt-3'>Course description</label>
                  <input name='description' id='description' type="text" class="form-control" placeholder="Optional. Leave blank to auto-generate">
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="button" id='createCourseBtn' class="btn btn-success" onclick='createCourse()'>Create</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Main board -->
      <div class="col-12">
        <div class="row ms-0">
          <div class='col-12'>
            <div class="row">
              <c:if test="${not search}">
                <c:forEach items="${courseList}" var="c">
                  <div class="col-4 mt-3">
                    <div class="card">
                      <h5 class="card-header">${c.courseName}</h5>
                      <div class="card-body">
                        <h5 class="card-title">${c.courseDescription}</h5><br>
                        <a href="./course?action=details&courseId=${c.id}" class="btn btn-primary mt-2">
                          <span>Details</span>
                        </a>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </c:if>
              <c:if test="${search and not empty searchCourseList}">
                <c:forEach items="${searchCourseList}" var="c">
                  <div class="col-4 mt-3">
                    <div class="card">
                      <h5 class="card-header">${c.courseName}</h5>
                      <div class="card-body">
                        <h5 class="card-title">${c.courseDescription}</h5>
                        <a href="./course?action=details&courseId=${c.id}" class="btn btn-primary mt-2">Details</a>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </c:if>
              <c:if test="${not search and empty courseList}">
                <h1 class='mt-5' style='text-align: center; vertical-align: middle; color: silver; user-select: none;'>
                  Σ(‘◉⌓◉’)

                  <br>
                  No courses available
                </h1>
              </c:if>
              <c:if test="${search and empty searchCourseList}">
                <h1 class='mt-5' style='text-align: center; vertical-align: middle; color: silver; user-select: none;'>
                  <pre class='me-5'>
?  ╱|、
      (˚ˎ 。7  
              |、˜〵          
      じしˍ,)ノ
                  </pre>
                  No courses found with '<%=searchStr%>'
                </h1>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>

</html>