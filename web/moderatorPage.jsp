<%-- 
    Document   : moderatorPage
    Created on : May 25, 2014, 12:09:09 PM
    Author     : Marco
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
        <title>Moderator page</title>
        <script src="sorttable.js"></script>
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
                                <b>${sessionScope.user.getUsername()}</b><b class="caret"></b></a>

                            <ul class="dropdown-menu">
                                <li><a href="GetOwnerGroups?email=${sessionScope.user.getEmail()}">Your groups</a></li>
                                <li><a href="ViewProfile?email=${sessionScope.user.getEmail()}">View profile</a></li>
                                    <c:choose>
                                        <c:when test="${sessionScope.user.getIsModerator() == true}">
                                        <li><a href="ModeratorPage?email=${sessionScope.user.getEmail()}">Switch to moderator</a></li>
                                        </c:when>
                                    </c:choose>
                                <li class="divider"></li>
                                <li><a href="Logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">

            <h1>Moderator page</h1>

            <div class="panel panel-default">
                <!-- Table -->
                <table class="table" class="sortable">
                    <th> GroupName </th>
                    <th> Subscribers </th>
                    <th> Private </th>
                    <th> Posts</th>
                        <c:forEach items="${groups}" var="group">
                            <c:choose>
                                <c:when test="${group.isIsClosed()==true}">
                                <tr class="danger">
                                </c:when>
                                <c:otherwise>
                                <tr>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="LoadPost?groupId=${group.getGroupId()}">${group.getGroupName()}</a></td>
                            <td>${group.getSubscribers()}</td>
                            <td>${group.isIsPrivate()}</td>
                            <td>${group.getNrPosts()}</td>
                            <c:choose>
                                <c:when test="${group.isIsClosed()==false}">
                                    <td><a href="CloseGroup?email=${sessionScope.user.getEmail()}&groupId=${group.getGroupId()}"><button type="button" class="btn btn-primary btn-xs">
                                                <span class="glyphicon glyphicon-ban-circle"></span> Close</button></a></td></c:when>
                                            <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>