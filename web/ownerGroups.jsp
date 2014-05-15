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
        <link href="Style/css/bootstrap.min.css" rel="stylesheet">
        <title>Your groups</title>
    </head>
    <body>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src='Style/js/bootstrap.min.js'></script>

        <div style="width:80%; margin:0 auto;">

            <ul class="nav nav-pills">
                <li class="active"><a href="Start">Home</a></li>

                <li class="dropdown">
                    <a id="drop1" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.user.getUsername()}<b class="caret"></b></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="GetOwnerGroups?email=${sessionScope.user.getEmail()}">Your groups</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="ViewProfile?email=${sessionScope.user.getEmail()}">View profile</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="#Switch moderator">Switch to moderator</a></li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="Logout">Logout</a></li>
                    </ul>
                </li>
                <li><a href=""><c:out value="${sessionScope.user.getLastLogin()}"/></a></li>
            </ul>

            <h1>Your groups</h1>

            <div class="panel panel-default">
                <!-- Table -->
                <table class="table">
                    <th> Group </th>
                    <th> Created at </th>
                    <th> Closed </th>
                    <th>Private</th>
                        <c:forEach items="${ownerGroups}" var="group">
                        <tr>
                            <td>${group.getGroupName()}</td>
                            <td>${group.getCreationDate()}</td>
                            <td>${group.isIsClosed()}</td>
                            <td>${group.isIsPrivate()}</td>
                            <td><button type="button" class="btn btn-primary btn-xs">Invite</button></td>
                            <td><button type="button" class="btn btn-primary btn-xs">Edit</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
    </body>
</html>
