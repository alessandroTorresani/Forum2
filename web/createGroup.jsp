<%-- 
    Document   : createGroup
    Created on : May 15, 2014, 4:30:34 PM
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
        <title>Create group</title>
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
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">

            <h1>Create group</h1>
            
             <c:choose>
                <c:when test="${param.groupName == 'error'}">
                    <div class="alert alert-danger">You inserted an invalid group name, try with another</div>
                </c:when>
            </c:choose>

            <form role="form" action="CreateGroup" method="post">
                <div class="form-group">
                    <label for="groupName">Group name</label>
                    <input type="text" class="form-control" id="groupName" placeholder="Group name" name="groupName">
                </div>
                <div class="form-group">
                    <div class="radio">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="public" checked>
                            Public
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="private">
                            Private
                        </label>
                    </div>
                </div>
                <br>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
        </div>
    </body>
</html>
