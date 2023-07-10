<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Profile</title>
  <% 
    Teacher teacher = (Teacher) request.getSession().getAttribute("teacher"); 
  %>

  <script>
    function checkCurrPassword() {
      var currPass = document.getElementById('hiddenCurrentPassword').value;
      var inpCurrPass = document.getElementById('currentPassword').value;
      var log = document.getElementById('log');

      var isCorrectPassword = currPass === inpCurrPass;
      log.innerHTML = isCorrectPassword ? "" : "Wrong password!";
      return isCorrectPassword;
    }

    function checkPassword() {
      var log = document.getElementById('log');

      if (!checkCurrPassword()) {
        return false;
      }

      var newPassword = document.getElementById('newPassword').value;
      var reNewPassword = document.getElementById('reNewPassword').value;

      if (reNewPassword !== '' && newPassword !== reNewPassword) {
        log.innerHTML = "Re-password does not match!";
        return false;
      }

      return true;
    }

  </script>
</head>

<body>
  <!-- Navbar -->
  <jsp:include page="navbar.jsp" />

  <!-- Main board -->
  <div class="container mt-3">
    <div class="row">
      <!-- Profile picture -->
      <div class="col-3">
        <div class='border rounded'>
          <h2 class='mt-3' style='text-align: center;'>${teacher.getFullName()}</h2>
          <center><span class='mt-3' style='text-align: center;'>@${teacher.getUsername()}</span></center>
          <center><i class='material-icons' style='font-size: 120px; text-align: center;'>account_circle</i></center>
          <div class="mt-3">
            <center><button class='btn btn-secondary mb-3'>Change profile picture</button></center>
          </div>
        </div>
      </div>

      <!-- Profile information -->
      <div class="col-6">

        <!-- Change profile -->
        <div class="border rounded">
          <h2 class='mt-3 ms-3'>Profile</h2>
          <form action='./profile' method='POST'>
            <input type="hidden" name="action" value='changeProfile'>
            <input type="hidden" name="teacherId" id="teacherId" value='${teacher.getTeacherId()}'>
            <div class="row">
              <div class='col-6'>
                <label for="fullName" class="form-label ms-3">Full Name</label>
                <input type="text" name='fullName' class="form-control ms-3 w-75" id="fullName" value='${teacher.getFullName()}'>
              </div>
              <div class='col-6'>
                <label for="shortName" class="form-label ms-3">Short Name</label>
                <input type="text" name='shortName' class="form-control ms-3 w-75" id="shortName" value='${teacher.getShortName()}'>
              </div>
            </div>
            <div class='mt-3 ms-3 mb-3'>
              <button class='btn btn-primary'>Change</button>
            </div>
          </form>
        </div>

        <!-- Change password -->
        <div class="border rounded mt-3">
          <input type="hidden" name="hiddenCurrentPassword" id="hiddenCurrentPassword" value='${teacher.getPassword()}'>
          <h2 class='mt-3 ms-3'>Change password</h2>
          <form action='./profile' method='post' onsubmit='return checkPassword()'>
            <input type="hidden" name="action" value='changePassword'>
            <input type="hidden" name="teacherId" id="teacherId" value='${teacher.getTeacherId()}'>
            <div class="row">
              <div class="col-6">
                <div>
                  <label for="currentPassword" class="form-label ms-3">Current password</label>
                  <input type="password" class="form-control ms-3 w-75" id="currentPassword" onchange='checkPassword()'>
                </div>
              </div>
              <div class="col-6">
                <div>
                  <span class='ms-3' id='log' style='vertical-align: middle; color: red;' ></span>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-6">
                <div>
                  <label for="newPassword" class="form-label ms-3 mt-3">New password</label>
                  <input type="password" name='newPassword' class="form-control ms-3 w-75" id="newPassword" oninput='checkPassword()'>
                </div>
              </div>
              <div class="col-6">
                <div>
                  <label for="rePassword" class="form-label ms-3 mt-3">Retype New password</label>
                  <input type="password" class="form-control ms-3 w-75" id="reNewPassword" oninput='checkPassword()'>
                </div>
              </div>
            </div>
            <button class='ms-3 mt-3 mb-3 btn btn-primary'>Change</button>
          </form>
        </div>
      </div>
      <div class="col-3">
        
      </div>
    </div>
  </div>
</body>

</html>