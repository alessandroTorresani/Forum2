<%-- 
    Document   : ownerGroups
    Created on : May 15, 2014, 5:44:32 PM
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
        <link href="Style/css/bootstrap.css" rel="stylesheet">
        <title>Your groups</title>
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
                    <ul class="nav navbar-nav navbar-left">
                        <li><a href="createGroup.jsp"><span class="glyphicon glyphicon-th-list"></span><b> Create group</b></a></li>
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
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">

            <h1>Your groups</h1>
            <c:choose>
                <c:when test="${param.groupName == 'error'}">
                    <div class="alert alert-danger">You inserted a non valid group name, try with another</div>
                </c:when>
            </c:choose>

            <div class="panel panel-default">
                <!-- Table -->
                <table class="table">
                    <th> Group </th>
                    <th> Created at </th>
                    <th> Status </th>
                        <c:forEach items="${ownerGroups}" var="group">
                            <c:choose>
                                <c:when test="${group.isIsClosed()==true}">
                                <tr class="danger">
                                </c:when>
                                <c:otherwise>
                                <tr>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="LoadPost?groupId=${group.getGroupId()}">${group.getGroupName()}</a></td>
                            <td>${group.getCreationDate()}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${group.isIsPrivate()}">
                                        Private
                                    </c:when>
                                    <c:otherwise>
                                        Public
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:choose>
                                <c:when test="${group.isIsClosed()==false}">
                                    <td><a href="PreEditGroup?groupId=${group.getGroupId()}"><button type="button" class="btn btn-primary btn-xs">
                                                <span class="glyphicon glyphicon-cog"></span> Manage</button></a></td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <span class="glyphicon glyphicon-ban-circle"></span>
                                                </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
