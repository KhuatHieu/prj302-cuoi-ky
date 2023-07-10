<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
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
<body>
  <!-- Navbar -->
  <jsp:include page="navbar.jsp"/>
</body>
</html>
