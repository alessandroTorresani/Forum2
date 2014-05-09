<%-- 
    Document   : index
    Created on : Apr 9, 2014, 3:33:21 PM
    Author     : Alessandro
--%>

<%@page import="db.User"%>
<%@page import="java.util.List"%>
<%@page import="db.Group"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <link href='Style/css/bootstrap.min.css' rel='stylesheet' media='screen'>
        <script src='http://code.jquery.com/jquery.js'></script>
        <script src='Style/js/bootstrap.min.js'></script>
        <title>Forum</title>
    </head>

    <body>



        <div style="width:80%; margin:0 auto;">

            <ul class="nav nav-pills">
                <li class="active"><a href="">Home</a></li>
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

            <h1>Forum</h1>
            
            <c:choose>
                <c:when test="${sessionScope.user == null}">
                    <form role="form" action="Login" method="post">
                        <div class="form-group">
                            <label for="name">Login</label>
                            <input type="text" class="form-control" placeholder="Email" name="email">
                            <input type="password" class="form-control" placeholder="Password" name="password">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-default">Sign in</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:when>
            </c:choose>
            <br>
            <div class="panel panel-default">
                <!-- Table -->
                <table class="table">
                    <th> Group </th>
                    <th> Owner </th>
                    <th> Created at </th>
                    <th> Closed </th>
                        <c:forEach items="${publicGroups}" var="group">
                        <tr>
                            <td>${group.getGroupName()}</td>
                            <td>${group.getAdminId()}</td>
                            <td>${group.getCreationDate()}</td>
                            <td>${group.isIsClosed()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
