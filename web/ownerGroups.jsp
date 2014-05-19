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
                        <li><a href="#"><span class="glyphicon glyphicon-th-list"></span><b> See invitations</b></a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                                <b>${sessionScope.user.getUsername()}</b><b class="caret"></b></a>

                            <ul class="dropdown-menu">
                                <li><a href="GetOwnerGroups?email=${sessionScope.user.getEmail()}">Your groups</a></li>
                                <li><a href="ViewProfile?email=${sessionScope.user.getEmail()}">View profile</a></li>
                                <li><a href="#moderator">Swith to moderator</a></li>
                                <li class="divider"></li>
                                <li><a href="Logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">

            <% /*<ul class="nav nav-pills">
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
                 </ul>*/%>  

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
                            <td><button type="button" class="btn btn-primary btn-xs">
                                    <span class="glyphicon glyphicon-plus"></span> Invite</button></td>
                                    <td><a href="PreEditGroup?email=${sessionScope.user.getEmail()}&groupId=${group.getGroupId()}"><button type="button" class="btn btn-primary btn-xs">
                                                <span class="glyphicon glyphicon-pencil"></span> Edit</button></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
        <div id="editGroup" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Edit your group</h4>
                    </div>
                    <div class="modal-body">
                        <p> Insert a new name or change the group status</p>
                        <form role="form" action="EditGroup?email=${sessionScope.user.getEmail()}&groupId=${group.getGroupId()}" method="post">
                            <div class="form-group">
                                <label for="groupName">Group name</label>
                                <input type="text" class="form-control" id="groupName" placeholder="groupName" name="groupName">
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
                </div>
            </div>
        </div>
    </body>
</html>