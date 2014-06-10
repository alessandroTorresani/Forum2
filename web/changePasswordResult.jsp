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
        
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="Start"><span class="glyphicon glyphicon-home"></span><b> Forum</b></a>
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <c:choose>

                        <c:when test="${sessionScope.user == null}">
                            <!-- Collect the nav links, forms, and other content for toggling -->

                            <form class="navbar-form navbar-left" role="form" action="Login" method="post">
                                <div class="form-group">
                                    <input type="email" class="form-control" id="email" placeholder="Email" name="email">
                                    <input type="password" class="form-control" id="password" placeholder="Password" name="password">
                                </div>
                                <button type="submit" class="btn btn-default">Login</button>
                            </form>

                            <ul class="nav navbar-nav navbar-right">
                                <li><a  data-toggle="modal" data-target="#forgetPassword">Forgot password</a></li>
                                <li><a  href="registration.jsp">Sign up</a></li>
                            </ul>

                        </c:when>
                        <c:otherwise>

                            <ul class="nav navbar-nav navbar-left">
                                <li><a href="createGroup.jsp"><span class="glyphicon glyphicon-plus-sign"></span><b> Create group</b></a></li>
                            </ul>

                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                                        <c:choose>
                                            <c:when test="${sessionScope.user.isModeratorMode()}">
                                                <b>${sessionScope.user.getUsername()} (Moderator mode)</b><b class="caret"></b></a>
                                            </c:when>
                                            <c:otherwise>
                                            <b>${sessionScope.user.getUsername()}</b><b class="caret"></b></a>
                                        </c:otherwise>
                                    </c:choose>

                                    <ul class="dropdown-menu">
                                        <li><a href="GetOwnerGroups?email=${sessionScope.user.getEmail()}">Your groups</a></li>
                                        <li><a href="ViewProfile?email=${sessionScope.user.getEmail()}">View profile</a></li>
                                            <c:choose>
                                                <c:when test="${sessionScope.user.getIsModerator() == true}">
                                                    <c:choose>
                                                        <c:when test="${sessionScope.user.isModeratorMode()}">
                                                        <li><a href="ModeratorPage?email=${sessionScope.user.getEmail()}">Moderator page</a></li>
                                                        <li><a href="ExitModeratorMode?email=${sessionScope.user.getEmail()}">Exit moderator mode</a></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <li><a href="EnterModeratorMode?email=${sessionScope.user.getEmail()}">Switch to moderator mode</a></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose><li class="divider"></li>
                                        <li><a href="Logout">Logout</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </c:otherwise>

                    </c:choose>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">

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
