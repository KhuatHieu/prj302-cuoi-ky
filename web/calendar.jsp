<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList, java.util.Calendar" %>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Calendar</title>
  <%
    Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
  %>
</head>
<body onload='setDate()'>
  <!-- Navbar -->
  <jsp:include page="navbar.jsp"/>

  <div class="row">
    <!-- sidebar -->
    <div class="col-2 min-vh-100 position-fixed" style="background-color: #212529;">
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
            <a href='./calendar' class="btn btn-dark w-100 active" style="color: white;" id='calendarHref'>
              <div class="row">
                <div class="col-2">
                  <span class='material-icons'>calendar_month</span>
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
                    <a href="./course?action=details&courseId=${c.id}" class="btn w-100 btn-dark align-items-center" 
                      style="font-size: medium; color: white; text-align: left;">
                      <div class="row align-items-center ms-0">
                        <div class="col-2">
                          <span class="material-symbols-outlined align-middle">school</span>
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

    <!-- calendar -->
    <div class="col-10" style='margin-left: 16.667%;'>
      <style>
        table td:hover {
          background-color: #DEE2E6;
        }

        th, td {
          width: 14.28%;
          border-right: solid #DEE2E6;
          border-right-width: 1px;
        }
      </style>
      <%
        int startDayOfMonth = (Integer) request.getAttribute("startDayOfMonth") - 1;
        int endDayOfMonth = (Integer) request.getAttribute("endDayOfMonth");

        ArrayList<Test> testList = (ArrayList<Test>) request.getAttribute("testList");
      %>

      <script>
        function setDate() {
          var currentUrl = new URL(document.location);
          var params = new URLSearchParams(currentUrl.search);

          var currMonth = params.get('month');
          var currYear = params.get('year');

          document.getElementById('myMonth').value = currMonth
          document.getElementById('myYear').value = currYear
        }

        function checkDate() {
          var myMonth = document.getElementById('myMonth').value
          var myYear = document.getElementById('myYear').value

          var currentUrl = new URL(document.location);
          var params = new URLSearchParams(currentUrl.search);

          params.set('month', myMonth);
          params.set('year', myYear);

          var nextUrl = currentUrl.origin + currentUrl.pathname + '?' + params.toString();
          window.location.href = nextUrl;
        }

        function downDate() {
          var myMonth = document.getElementById('myMonth').value
          var myYear = document.getElementById('myYear').value

          if (myMonth != 1) {
            document.getElementById('myMonth').value = --myMonth;
          } else {
            document.getElementById('myMonth').value = 12
            document.getElementById('myYear').value = --myYear
          }

          checkDate()
        }

        function upDate() {
          var myMonth = document.getElementById('myMonth').value
          var myYear = document.getElementById('myYear').value

          if (myMonth != 12) {
            document.getElementById('myMonth').value = ++myMonth;
          } else {
            document.getElementById('myMonth').value = 1
            document.getElementById('myYear').value = ++myYear
          }

          checkDate()
        }
      </script>
      <h1 class='ms-4 mb-1 mt-3 align-middle'>
        Calendar
      </h1>
      <div class="input-group ms-4 mb-4 mt-1 align-middle w-25" style='text-align: center;'>      
        <button class='btn btn-light border' onclick='downDate()'>&lt</button>               
        <select name='subject' id='myMonth' class="form-select form-control-sm selectpicker" data-live-search="true"
          style='overflow-y: auto; max-height: 200px;' onchange='checkDate()'>
          <%
            for (int i = 1; i <= 12; i++) {
              out.print("<option value='" + i + "'" + ">" + i + "</option>");
            }
          %>
        </select>
        <span class="input-group-text">/</span>
        <select name='term' id='myYear' class="form-select selectpicker" data-live-search="true" onchange='checkDate()'>
          <%
            for (int i = 2023; i >= 2000; i--) {
              out.print("<option value='" + i + "'" + ">" + i + "</option>");
            }
          %>
        </select>
        <button class='btn btn-light border' onclick='upDate()'>&gt</button> 
      </div>

      <table class='table w-100'>
        <thead>
          <tr>
            <th scope='col'>Mon</th>
            <th scope='col'>Tue</th>
            <th scope='col'>Wed</th>
            <th scope='col'>Thu</th>
            <th scope='col'>Fri</th>
            <th scope='col'>Sat</th>
            <th scope='col'>Sun</th>
          </tr>
        </thead>
        <tbody class='table-group-divider'>
          <tr>
            <% 
              for (int i = 0; i < startDayOfMonth; i++)
                out.print("<td class='table-active'></td>");

              for (int i = 1; i <= endDayOfMonth; i++) {
                out.print("<td>" + i + "<br>");
                for (Test test : testList) {
                  if (test.getDayOfMonth() == i) {
                    String gotoHref = "./course?action=details&courseId=" + test.getCourseId() + "&testId=" + test.getTestId();
                    out.print("<a href='" + gotoHref + "' class='badge " + test.getBadgeBg() + "' style='text-decoration: none;'>" + test.getStatus() + "</a>");
                    out.print(" " + test.getTestName() + "<br>");
                  }
                }
                out.print("</td>");
                if ((startDayOfMonth + i) % 7 == 0) {
                  out.print("<tr></tr>");
                }
              }
              int emptyCells = 7 - ((startDayOfMonth + endDayOfMonth) % 7);
              if (emptyCells != 7) {
                for (int i = 0; i < emptyCells; i++) {
                  out.print("<td class='table-active'></td>");
                }
              }
            %>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>
