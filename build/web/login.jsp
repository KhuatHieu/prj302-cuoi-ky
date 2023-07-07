<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>CMS Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>

  <%
    String log = "";
    if (request.getAttribute("log") != null) {
      log = (String) request.getAttribute("log");
    }
  %>
</head>

<body>
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg border-bottom sticky-top" style='height: 4rem;'>
    <div class="container" style='margin-left: 0rem; '>
      <!-- Brand -->
      <a href="#" class="navbar-brand">
        <img src="https://fpt.edu.vn/Content/images/assets/Logo-FU-03.png" height='35'>
      </a>
  </nav>

  <div class="container">
    <div class="row mt-5">
      <div class="col-4">

      </div>
      <div class="col-4">
        <div class="row">
          <div class="rounded border">
            <form action="login" method='post'>
              <center><h2 class='mt-3 mb-3'>CMS Teachers Login</h2></center>
              <div class="form-floating mb-3">
                <input type="text" class="form-control" id="username" name='username' placeholder="Your username" 
                  onchange='document.getElementById("log").innerHTML=""'>
                <label for="username">Username</label>
              </div>
              <div class="form-floating mb-1">
                <input type="password" class="form-control" id="password" name='password' placeholder="Your password"
                onchange='document.getElementById("log").innerHTML=""'>
                <label for="password">Password</label>
              </div>
              <div class="mb-1">
                <span class="d-inline-block text-danger" style='font-size: small; max-width: 200px;' id='log'>
                  <%=log%>
                </span>
              </div>
              <div class="row">
                <center><button class='btn btn-success w-75 mb-3'>Login</button></center>
              </div>
            </form>            
          </div>
        </div>
        <div class="row mt-5">
          <div class="rounded border">
            <center><h2 class='mt-3 mb-3'>Don't have an account?</h2></center>
            <div class="row">
              <center><button class='btn btn-outline-success w-75 mb-3'>Contact FPT IT Adminstrators</button></center>
            </div>
          </div>
        </div>
      </div>
      <div class="col-4">
  
      </div>
    </div>
  </div>
</body>

</html>