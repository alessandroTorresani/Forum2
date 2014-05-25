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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="Style/css/bootstrap.css" rel="stylesheet">
        <title>Forum</title>
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

            <h1>Forum</h1>

            <c:choose>
                <c:when test="${sessionScope.user == null}">
                    <c:choose>
                        <c:when test="${sessionScope.login == 'error'}">
                            <div class="alert alert-danger">You inserted a non valid username and/or an invalid password</div>
                            <%HttpSession servletSession = request.getSession();
                                servletSession.removeAttribute("login");%>
                        </c:when>
                        <c:otherwise>
                            <%HttpSession servletSession1 = request.getSession();
                                servletSession1.removeAttribute("login");%>
                        </c:otherwise>
                    </c:choose> 
                </c:when>
            </c:choose>
            <br>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="createGroup.jsp" class="btn btn-primary" role="button" >Create group</a>
                    <br>
                    <br>
                </c:when>
            </c:choose>

            <div class="panel panel-default">
                <!-- Table -->
                <table class="table">
                    <th> Group </th>
                    <th> Owner </th>
                    <th> Created at </th>
                    <th> Private </th>
                        <c:forEach items="${publicGroups}" var="publicGroup">
                            <c:choose>
                                <c:when test="${publicGroup.isIsClosed()==true}">
                                <tr class="danger">
                                </c:when>
                                <c:otherwise>
                                <tr>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="LoadPost?groupId=${publicGroup.getGroupId()}">${publicGroup.getGroupName()}</a></td>
                            <td>${publicGroup.getAdminUsername()}</td>
                            <td>${publicGroup.getCreationDate()}</td>
                            <td>${publicGroup.isIsPrivate()}</td>
                        </tr>
                    </c:forEach>
                    <c:forEach items="${privateGroups}" var="privateGroup">
                        <c:choose>
                            <c:when test="${privateGroup.isIsClosed()==true}">
                                <tr class="danger">
                                </c:when>
                                <c:otherwise>
                                <tr>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="LoadPost?groupId=${privateGroup.getGroupId()}">${privateGroup.getGroupName()}</a></td>
                            <td>${privateGroup.getAdminUsername()}</td>
                            <td>${privateGroup.getCreationDate()}</td>
                            <td>${privateGroup.isIsPrivate()}</td>
                        </tr>

                    </c:forEach>
                </table>
            </div>
            <br>
            <c:choose>
                <c:when test="${bids.size()>0}">
                    <form action="AcceptBids" method="post">
                        <div class="panel panel-default">
                            <!-- Table -->
                            <table class="table">
                                <th> Group </th>
                                <th> Owner </th>
                                <th> Accept </th>
                                <th> Refuse </th>
                                    <c:forEach items="${bids}" var="bid">
                                    <tr>
                                        <td>${bid.getGroupName()}</td>
                                        <td>${bid.getAdminUsername()}</td>
                                        <td><div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="accepted" value="${bid.getBidId()}">
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" name="refused" value="${bid.getBidId()}">
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </c:when>
                <c:when test="${sessionScope.user != null}">
                    <p> no invitation </p>
                </c:when>
            </c:choose>
        </div>

        <div id="forgetPassword" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Password recovery</h4>
                    </div>
                    <div class="modal-body">
                        <p>Insert your email and your new password</p>
                        <p>If you visit the link that we will send to your email in 90 seconds, your modification will be applied</p>
                        <form role="form" action="ForgotPassword" method="post">
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email1" placeholder="Email" name="email">
                            </div>
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
