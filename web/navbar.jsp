<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <% 
      Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
    %>
  </head>
  <body style='overflow-x: hidden;'>
    <div class="row border-bottom sticky-top" style='height: 4rem; background-color: white;'>
      <div class="col-2">
        <a href="./">
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
            <li><a class='dropdown-item' href='./logout'>
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
