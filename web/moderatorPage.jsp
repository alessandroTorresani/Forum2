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
        <link href="DataTable/css/jquery.dataTables.min.css" rel="stylesheet">
        <title>Moderator page</title>
    </head>
    <body>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src='Style/js/bootstrap.min.js'></script>

        <script src="DataTable/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src='DataTables/js/jquery.js"/>'></script>


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

            <h1>Moderator page</h1>

            <script>
                $(document).ready(function() {
                    $("#moderatorTable").dataTable();
                });
            </script> 

            <table id="moderatorTable">
                <thead>
                    <tr>
                        <th>
                            Group name
                        </th>
                        <th>
                            Subscribers
                        </th>
                        <th>
                            Posts
                        </th>
                        <th>
                            Status
                        </th>
                        <th>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="g" items ="${groups}">
                        <tr>
                            <td><a href="LoadPost?groupId=${g.getGroupId()}">${g.getGroupName()}</a></td>
                            <td>${g.getSubscribers()}</td>
                            <td>${g.getNrPosts()}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${g.isIsPrivate()}">
                                        Private
                                    </c:when>
                                    <c:otherwise>
                                        Public
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:choose>
                                <c:when test="${g.isIsClosed()==false}">
                                    <td>
                                        <a href="CloseGroup?email=${sessionScope.user.getEmail()}&groupId=${g.getGroupId()}">
                                            Close
                                        </a>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <b>Closed</b>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
