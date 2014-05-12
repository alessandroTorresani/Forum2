<%-- 
    Document   : registrationResult
    Created on : Apr 24, 2014, 3:12:35 PM
    Author     : harwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="Style/css/bootstrap.min.css" rel="stylesheet">
        <title>Registration Result</title>
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

            <h1>
                <c:out value="${Result}"/>
            </h1>   

            <c:choose>
                <c:when test='${Result != "Your registration was successful"}'>

                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">Error</h3>
                        </div>
                        <div class="panel-body">

                            <c:choose>
                                <c:when test="${usernameCheck == false}">
                                    <p>You inserted a non valid username</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${pass1Check == false}">
                                    <p>You inserted a non valid password</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${email1Check == false}">
                                    <p>You inserted a non valid email</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${equalPass == false}">
                                    <p>Passwords don't corresponding</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${equalEmail == false}">
                                    <p>Emails don't corresponding </p>
                                </c:when>  
                            </c:choose>
                            <a href="registration.jsp">
                                <BUTTON class="btn btn-danger">Retry</BUTTON>
                            </a>
                        </c:when>
                        <c:otherwise>   
                            <div class="panel panel-success">
                                <div class="panel-heading">                                    
                                    <h3 class="panel-title">Success</h3>
                                </div>
                                <div class="panel-body">
                                    <a href="Start">
                                        <BUTTON class="btn btn-success">Return home</BUTTON>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

            </div>
    </body>
</html>
