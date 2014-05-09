<%-- 
    Document   : registration
    Created on : Apr 14, 2014, 2:13:27 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <link href='Style/css/bootstrap.min.css' rel='stylesheet' media='screen'>
        <script src='http://code.jquery.com/jquery.js'></script>
        <script src='Style/js/bootstrap.min.js'></script>
        <title>Register now</title>
    </head>

    <body>
        <div style="width:80%; margin:0 auto;">

            <ul class="nav nav-pills">
                <li class="active"><a href="Start">Home</a></li>
                    <c:choose>
                        <c:when test="${sessionScope.user != null}">
                        <li><a href="Logout">Logout</a></li>
                        <li> <a href=""><c:out value="${sessionScope.user.getUsername()}"/></a></li>
                        <li><a href=""><c:out value="${sessionScope.user.getLastLogin()}"/></a></li>
                        </c:when>
                        <c:otherwise>       
                        <li><a href="registration.jsp">Sign up</a></li>
                        <li><a href="forgetPassword.jsp">Forget password</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>

            <h1>Registration</h1>

            <form role="form" action="Registration" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" placeholder="Username" name="username">
                </div>
                <div class="form-group">
                    <label for="email1">Email</label>
                    <input type="email" class="form-control" id="email1" placeholder="Email" name="email1">
                </div>
                <div class="form-group">
                    <label for="email2">Reinsert email</label>
                    <input type="email" class="form-control" id="email2" placeholder="Email" name="email2">
                </div>
                <div class="form-group">
                    <label for="password1">Password</label>
                    <input type="password" class="form-control" id="password1" placeholder="Password" name="password1">
                </div>
                <div class="form-group">
                    <label for="password2">Reinsert Password</label>
                    <input type="password" class="form-control" id="password2" placeholder="Password" name="password2">
                </div>
                <div class="form-group">
                    <label for="exampleInputFile">File input</label>
                    <input type="file" name="avatar">
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>

        </div>
    </body>
</html>
