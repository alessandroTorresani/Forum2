<%-- 
    Document   : groupPage
    Created on : May 22, 2014, 3:45:08 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="Style/css/bootstrap.css" rel="stylesheet">
        <title>${groupName}</title>
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
                            <button type="button" data-toggle="modal" data-target="#forgetPassword" class="btn btn-primary navbar-btn">Forgot password</button>


                            <ul class="nav navbar-nav navbar-right">
                                <li><a  data-toggle="modal" data-target="#forgetPassword">Forgot password</a></li>
                                <li><a  href="registration.jsp">Sign up</a></li>
                            </ul>

                        </c:when>
                        <c:otherwise>

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
                        </c:otherwise>

                    </c:choose>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">
            <h1>${groupName}</h1>
            <br>

            <c:forEach items="${posts}" var="post">
                <div class="panel panel-default">
                    <!-- Table -->
                    <table class="table table-condensed table-striped">
                        <th class="col-sm-2" > ${post.getUsername()} </th>
                        <th> Message</th>
                        <th class="col-sm-1"> File </th>
                        <tr>
                            <td><img src="Avatars/${post.getImgUrl()}" class="img-thumbnail" style="height: 120px;width: 110px"></td>
                            <td>${post.getMessage()}
                                <br>
                                <p style="font-size: 10px">Message posted at ${post.getCreationDate()}</p></td>
                            <td>File</td>
                        </tr>
                    </table>
                </div>
            </c:forEach>
             <div class="panel panel-default">
                    <!-- Table -->
                    <table class="table table-condensed table-striped">
                        <th class="col-sm-2" > Administrator </th>
                        <th> Message</th>
                        <th class="col-sm-1"> File </th>
                        <tr>
                            <td><img src="Avatars/0.jpg" class="img-thumbnail" style="height: 120px;width: 110px"></td>
                            <td>${groupName} was created at ..</td>
                            <td>File</td>
                        </tr>
                    </table>
                </div>
    </body>
</html>
