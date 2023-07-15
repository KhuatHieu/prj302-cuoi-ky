<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
    crossorigin="anonymous"></script>
    
    <link href="https://cdn.datatables.net/v/bs5/dt-1.13.5/datatables.min.css" rel="stylesheet"/>
    <script src='https://code.jquery.com/jquery-3.5.1.js'></script>
    <script src="https://cdn.datatables.net/v/bs5/dt-1.13.5/datatables.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/css/bootstrap-select.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.14.0-beta3/dist/js/bootstrap-select.min.js"></script>

    <script src="https://kit.fontawesome.com/10525e69e7.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">

    <!-- <link rel="stylesheet" href="https://raw.githubusercontent.com/hung1001/font-awesome-pro/v5/css/all.min.css">
    <script src="https://raw.githubusercontent.com/hung1001/font-awesome-pro/v5/js/pro.min.js"></script> -->
    
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <% 
      Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
    %>
  </head>
  <c:url value="/" var="home"></c:url>
  <body style='overflow-x: hidden;'>
    <div class="row border-bottom sticky-top" style='height: 4rem; background-color: white;'>
      <div class="col-2">
        <a href="${home}">
          <img class='m-3' src="https://fpt.edu.vn/Content/images/assets/Logo-FU-03.png" height='35'>
        </a>
      </div>
      <div class="col-10" style='text-align: end'>
        <div class="dropdown m-2">
          <!-- Account dropdowns -->
          <div class="btn btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class='material-icons' style='font-size: 40px; vertical-align: middle;'>account_circle</i>
          </div>

          <ul class='dropdown-menu dropdown-menu-end'>
            <!-- User info -->
            <li>
              <button class='dropdown-item disabled'>
                <%=teacher.getFullName()%>
                <br>
                @<%=teacher.getUsername()%>
              </button>
            </li>

            <!-- Settings -->
            <li><hr class="dropdown-divider"></li>
            <li><a class='dropdown-item' href='./'>
                <i class='material-icons' style='vertical-align: middle;'>home</i>
                Home
              </a>
            </li>

            <!-- Settings -->
            <li><a class='dropdown-item' href='./profile'>
                <i class='material-icons' style='vertical-align: middle;'>person</i>
                Profile
              </a>
            </li>

            <!-- Logout -->
            <li><hr class="dropdown-divider"></li>
            <li>
              <a class='dropdown-item' href='./logout'>
                <i class='material-icons' style='vertical-align: middle;'>logout</i>
                Log out
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>
