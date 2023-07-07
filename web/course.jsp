<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${course.getCourseName()}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
  <% 
    Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
    String courseId = (String) request.getAttribute("courseId");
  %>
  <script>
    function applyDateValues() {
      const now = new Date()

      const yearInput = document.getElementById("year");
      yearInput.value = now.getFullYear();
      yearInput.min = now.getFullYear();

      document.getElementById("month").value = now.getMonth() + 1;
      document.getElementById("day").value = now.getDate();
      document.getElementById('hour').value = now.getHours()
      document.getElementById('minute').value = now.getMinutes()
    }

    function checkInput() {
      const dayBox = document.getElementById('day')

      if (parseInt(dayBox.value) > parseInt(dayBox.max)) {
        dayBox.value = dayBox.max
      }

      if (document.getElementById('month').value > 12) {
        document.getElementById('month').value = 12
      }
    }

    function checkValidDate() {
      const now = new Date();

      const monthValue = parseInt(document.getElementById("month").value);
      const dayInput = document.getElementById("day");
      const dayValue = dayInput.value;

      dayInput.max = [1, 3, 5, 7, 8, 10, 12].includes(monthValue) ? 31 :
                    [4, 6, 9, 11].includes(monthValue) ? 30 :
                    ((now.getFullYear() % 4 == 0 && now.getFullYear() % 100 != 0) || now.getFullYear() % 400 == 0) ? 29 : 28;
    }
  </script>
  <script>
    function calcEstimatedTime() {
      checkValidDate()
      checkInput()
      var estimatedTimeBox = document.getElementById('estimatedTime')

      const day = document.getElementById('day').value
      const month = document.getElementById('month').value
      const year = document.getElementById('year').value
      const hour = document.getElementById('hour').value
      const minute = document.getElementById('minute').value

      if (day && month && year && hour && minute) {
        const now = new Date()

        const nowDay = now.getDate()
        const nowMonth = now.getMonth() + 1
        const nowYear = now.getFullYear()
        const nowHour = now.getHours()
        const nowMinute = now.getMinutes()

        const startDate = new Date(year, month - 1, day, hour, minute)
        const endDate = new Date(nowYear, nowMonth - 1, nowDay, nowHour, nowMinute)

        const state = endDate.getTime() - startDate.getTime() < 0 ? "" : "Past "
        const timeDiff = Math.abs(endDate.getTime() - startDate.getTime())

        if (timeDiff === 0) {
          estimatedTimeBox.style.color = ''
          estimatedTimeBox.innerHTML = 'Estimated time: [Now]'
          return
        }

        const diffDays = Math.floor(timeDiff / (1000 * 3600 * 24))
        const diffMonths = Math.floor(diffDays / 30)
        const diffYears = Math.floor(diffMonths / 12)
        const diffHours = Math.floor((timeDiff % (1000 * 3600 * 24)) / (1000 * 3600))
        const diffMinutes = Math.ceil((timeDiff % (1000 * 3600)) / (1000 * 60))

        let estimatedTime = ''
        if (diffYears > 0) {
          estimatedTime += diffYears + ((diffYears === 1) ? ' year ' : ' years ')
        }
        if (diffMonths > 0) {
          estimatedTime += diffMonths + ((diffMonths === 1) ? ' month ' : ' months ')
        }
        if (diffDays % 30 > 0) {
          estimatedTime += (diffDays % 30) + ((diffDays % 30 === 1) ? ' day ' : ' days ')
        }
        if (diffHours > 0) {
          estimatedTime += diffHours + ((diffHours === 1) ? ' hour ' : ' hours ')
        }
        if (diffMinutes > 0) {
          estimatedTime += diffMinutes + ((diffMinutes === 1) ? ' minute ' : ' minutes ')
        }

        estimatedTimeBox.style.color = (state === "Past ") ? 'red' : ''
        estimatedTimeBox.innerHTML = 'Estimated time: ' + state + estimatedTime
      } else {
        estimatedTimeBox.innerHTML = ''
      }
    }
  </script>
</head>

<body onload='applyDateValues()'>
  <!-- Navbar -->
  <jsp:include page="navbar.jsp"/>
  
  <div class="row">

    <!-- sidebar -->
    <div class="col-2 min-vh-100" style="background-color: #212529;">
      <div class="p-3">
        <ul class="list-unstyled">
          <li class="mb-1">
            <a href='./' class="btn btn-dark w-100" style="color: white;">
              <div class="row">
                <div class="col-2">
                  <span class='material-symbols-outlined'>home</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>Home</h5>
                </div>
              </div>
            </a>
          </li>
          <li class="mb-1">
            <button class="btn btn-dark w-100" style="color: white;">
              <div class="row">
                <div class="col-2">
                  <span class='material-symbols-outlined'>event</span>
                </div>
                <div class="col-10">
                  <h5 class='text-start'>Calendar</h5>
                </div>
              </div>
            </button>
          </li>
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
                    <a href="./course?action=details&courseId=${c.id}" class="btn btn-dark align-items-center ${c.getId() == courseId ? 'active' : ''}" style="font-size: medium; color: white; text-align: left;">
                      <div class="row align-items-center ms-0">
                        <div class="col-2">
                          <span class="${c.getId() == courseId ? 'material-icons' : 'material-symbols-outlined'} align-middle">school</span>
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
          <li class="mb-1" style='position: absolute; bottom: 0rem;'>
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

    <div class="col-10 mt-3">
      <div class="row ms-0">

        <!-- Tests summary and Test details -->
        <div class="col-8">
          <!-- Tests summary -->
          <div class="border rounded">
            <h4 class='ms-3 mt-2'>Tests</h4>
            <c:if test="${empty testList}">
              <h1 class='ms-3 fs-5' style='color: silver; user-select: none;'>
                No tests available
              </h1>
            </c:if>

            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th scope="col" class='border-end ms-3'>#</th>
                  <th scope="col" class='border-end'>Class</th>
                  <th scope="col" class='border-end'>Test name</th>
                  <th scope="col">Due date</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th scope="row" class='border-end'>1</th>
                  <td class='border-end'>IS1703</td>
                  <td class='border-end'>Progress Test 1</td>
                  <td>07/07/2023 19:45</td>
                </tr>
                <tr>
                  <th scope="row" class='border-end'>2</th>
                  <td class='border-end'>IS1703</td>
                  <td class='border-end'>Workshop 1</td>
                  <td>23/06/2023 09:50</td>
                </tr>
                <tr>
                  <th scope="row" class='border-end'>3</th>
                  <td class='border-end'>SE1734</td>
                  <td class='border-end'>Progress Test 2</td>
                  <td>09/07/2023 14:50</td>
                </tr>
              </tbody>
            </table>

            <button class='btn btn-sm btn-outline-success ms-3 mb-3 mt-1' style='width: 5rem;'
              data-bs-toggle="modal" data-bs-target="#createTest">
              <i class='material-icons align-middle'>
                add
              </i>
            </button>
            <div class="modal fade" id='createTest'>
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class='modal-title fs-5'>Create a test</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <form action="course" method="post" enctype='multipart/form-data'>
                    <input type="hidden" name="action" id="" value='createTest'>
                    <input type="hidden" name="queryString" value="${pageContext.request.queryString}" />
                    <input type="hidden" name="courseId" id="" value='${courseId}'>
                    <div class='modal-body'>
                      <div class="row">
                        <div class="col-8">
                          <label for="testName" class="form-label">Test name</label>
                          <div class="input-group">
                            <input type="text" class="form-control" id="testName">
                          </div>
                        </div>
                        <div class="col-4">
                          <label for="groupName" class="form-label">Class</label>
                          <div class="input-group">
                            <input type="text" class="form-control" id="groupName">
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <label for="dueDate" class='form-label mt-3'>
                            <span>Due date [dd/mm/yyyy]</span>
                          </label>
                          <div class="input-group">
                            <input type="number" class="form-control" placeholder="Day" id ='day' min='1' onchange='calcEstimatedTime()'>
                            <span class="input-group-text">/</span>
                            <input type="number" class="form-control" placeholder="Month" id='month' min='1' max='12' onchange='calcEstimatedTime()'>
                            <span class="input-group-text">/</span>
                            <input type="number" class="form-control w-25" placeholder="Year" id='year' min='2023' onchange='calcEstimatedTime()'>
                          </div>
                        </div>
                        
                        <div class="col-4">
                          <label for="dueDate" class='form-label mt-3'>Due time</label>
                          <div class="input-group">
                            <input type="number" class="form-control" placeholder="hh" id='hour' value='0' min='0' max='23' onchange='calcEstimatedTime()'>
                            <span class="input-group-text">:</span>
                            <input type="number" class="form-control" placeholder="mm" id='minute' value='0' min='0' max='59' onchange='calcEstimatedTime()'>
                          </div>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-8">
                          <div class='form-text align-middle' id='estimatedTime'>Estimated time: [Now]</div>
                        </div>
                        <div class="col-4">
                          <a href='#' style='color: #0D6EFD;' class='form-text align-middle' id='' onclick='applyDateValues(); calcEstimatedTime()'>Reset</a>
                        </div>
                      </div>
                      
                      <label for="testResource" class="form-label mt-3">Attach resources</label>
                      <input class="form-control" type="file" id="testResource" name='file' multiple>
                      <div class="form-text">
                        Maximum file size: 25MB
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" id='createCourseBtn' class="btn btn-success">Create</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>

          <!-- Test details -->
          <div class="rounded border mt-3">
            <h1 class='mt-3 mb-3' style='text-align: center; vertical-align: middle; color: silver; user-select: none;'>
              Σ(‘◉⌓◉’)
  
              <br>
              Select a Test
            </h1>
          </div>
        </div>

        <!-- Resources and Preferences -->
        <div class="col-4">
          <!-- Resources -->
          <div class="rounded border">
            <h4 class='ms-3 mt-2'>General resources</h4>

            <c:if test="${empty resourceList}">
              <h1 class='ms-3 fs-5' style='color: silver; user-select: none;'>
                No resources available
              </h1>
            </c:if>
            <c:forEach items="${resourceList}" var="r">
              <div class="row">
                <div class="col-10">
                  <div class="ms-3 mb-1">
                    <a href="./download?downloadKey=${r.getDownloadKey()}">
                      ${r.getName()}
                    </a>
                  </div>
                </div>
                <div class="col-2">
                  <span class='material-symbols-outlined'>${r.getExtensionIcon()}</span>
                </div>
              </div>
            </c:forEach>

            <div class="row">
              <div class="col-8">
                <button class='btn btn-sm btn-outline-success ms-3 mb-3 mt-2' style='width: 5rem;' data-bs-toggle='modal' data-bs-target='#uploadResource' tabindex='-1'>
                  <i class='material-icons align-middle'>upload</i>
                </button>
              </div>
              <div class="col-4">
                <c:if test="${not empty resourceList}">
                  <button class='btn btn-sm btn-outline-danger ms-3 mb-3 mt-2' style='width: 5rem;' data-bs-toggle='modal' data-bs-target="#deleteResource" tabindex='-1'>
                    <i class='material-icons align-middle'>delete</i>
                  </button>
                </c:if>
              </div>
            </div>

            <div class="modal fade" id='uploadResource' data-bs-backdrop='static'>
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class='modal-title fs-5'>Upload resources</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <form action="course" method="post" enctype='multipart/form-data'>
                    <input type="hidden" name="action" id="" value='uploadResource'>
                    <input type="hidden" name="queryString" value="${pageContext.request.queryString}" />
                    <input type="hidden" name="courseId" id="" value='${courseId}'>
                    <div class='modal-body'>
                      <label for="formFiles" class="form-label">You can choose multiple files</label>
                      <input class="form-control" type="file" id="formFiles" name='file' multiple>
                      <div class="form-text">
                        Maximum file size: 25MB
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" id='createCourseBtn' class="btn btn-success">Upload</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>

            <div class="modal fade" id='deleteResource' data-bs-backdrop='static'>
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class='modal-title fs-5'>Delete resources</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <form action="course" method="post" name='resourceIdList'>
                    <input type="hidden" name="action" id="" value='deleteResource'>
                    <input type="hidden" name="queryString" value="${pageContext.request.queryString}" />
                    <input type="hidden" name="courseId" id="" value='${courseId}'>
                    <div class="modal-body">
                      <label for="form-check">Mark all the resources which you want to be deleted</label>
                      <div class="form-check mt-1" id='form-check'>
                        <c:forEach items="${resourceList}" var="r">
                          <input class="form-check-input" type='checkbox' name='selectedResourceIds' value='${r.getId()}'>
                          <label for="${r.getName()}">
                            ${r.getName()}
                          </label>
                          <br>
                        </c:forEach>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      <button type="submit" id='createCourseBtn' class="btn btn-danger">Delete</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>

          <!-- Preferences -->
          <div class="border rounded mt-3">
            <h4 class='ms-3 mt-2'>Preferences</h4>

            <div class="ms-3 mb-3">
              <label for="basic-url" class="form-label">Course name</label>
              <div class="input-group">
                <input type="text" class="form-control" id="basic-url" name="courseName"
                  value='${course.getCourseName()}'>
                <button class='btn btn-sm btn-outline-success border me-4' style='vertical-align: middle;'>
                  <span class='material-icons align-middle'>done</span>
                </button>
              </div>
            </div>
            
            <div class="ms-3 mb-3">
              <label for="basic-url" class="form-label">Course description</label>
              <div class="input-group">
                <input type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3 basic-addon4"
                  value='${course.getCourseDescription()}'>
                <button class='btn btn-sm btn-outline-success border me-4' style='vertical-align: middle;'>
                  <span class='material-icons align-middle'>done</span>
                </button>
              </div>
            </div>

            <div class="ms-3 mb-3">
              <div class="form-check form-switch">
                <label class="form-check-label" for="switch">Visible to all students</label>
                <input class="form-check-input" type="checkbox" role="switch" id="switch" checked>
              </div>
            </div>
            <hr>
            <div class="ms-3 mb-3">
              <button class='btn btn-outline-danger'>Delete course</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
  crossorigin="anonymous"></script>
</body>

</html>