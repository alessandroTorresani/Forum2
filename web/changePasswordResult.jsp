<%-- 
    Document   : changePassword.jsp
    Created on : May 5, 2014, 3:48:15 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="Style/css/bootstrap.min.css" rel="stylesheet">
        <title>Change your password</title>
    </head>
    <body>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src='Style/js/bootstrap.min.js'></script>

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

            <c:choose>
                <c:when test="${errorMessage == 'Request out of time'}">
                    <h1>Request out of time</h1>
                </c:when>
                <c:when test="${correctRequest == true}">
                    <h1>Password changed succesfully</h1>
                </c:when>
                <c:when test="${correctRequest == false}">
                    <h1>Request not found</h1>
                </c:when>
                <c:otherwise>
                    <h1> Illegal use </h1>
                </c:otherwise>
            </c:choose>        
        </div>

    </body>
</html>
