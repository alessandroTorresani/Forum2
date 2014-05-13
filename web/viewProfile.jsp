<%-- 
    Document   : viewProfile
    Created on : May 13, 2014, 3:25:50 PM
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
        <title>View profile</title>
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
            <h1>Your Profile</h1>
            <div style="width: 40%; float:left;">
                <img src="Avatars/${imgUrl}" class="img-thumbnail" style="max-height: 50%;max-width: 100%">
                <br>
                <button class="btn btn-primary" data-toggle="modal" data-target="#changeAvatar">Change avatar</button>
                <br>
                <br>
                <button class="btn btn-primary" data-toggle="modal" data-target="#changePassword">Change password</button>
            </div>
            <br>
            <div style="width:55%; float:right">
                <div style="width:40%">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Username</h3>
                        </div>
                        <div class="panel-body">
                            ${sessionScope.user.getUsername()}

                        </div>
                    </div>
                </div>
                <br>
                <div style="width:40%">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Email</h3>
                        </div>
                        <div class="panel-body">
                            ${sessionScope.user.getEmail()}
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <div id="changeAvatar" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change avatar</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" action="ChangeAvatar?email=${sessionScope.user.getEmail()}" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="exampleInputFile">File input</label>
                                <input type="file" name="avatar">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="changePassword" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change avatar</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" action="ChangeAvatar" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="password1">Password</label>
                                <input type="password" class="form-control" id="password1" placeholder="Password" name="password1">
                            </div>
                            <div class="form-group">
                                <label for="password2">Reinsert Password</label>
                                <input type="password" class="form-control" id="password2" placeholder="Password" name="password2">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
