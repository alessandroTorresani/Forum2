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
        <title>${groupPage.getGroupName()}</title>
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

                            <c:choose>
                                <c:when test="${isSubscribed == true}">
                                    <ul class="nav navbar-nav navbar-left">
                                        <li><a data-toggle="modal" data-target="#addPost"><span class="glyphicon glyphicon-th-list"></span><b> Add post</b></a></li>
                                    </ul>
                                </c:when>
                            </c:choose>
                            <ul class="nav navbar-nav navbar-right">
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                                        <b>${sessionScope.user.getUsername()}</b><b class="caret"></b></a>

                                    <ul class="dropdown-menu">
                                        <li><a href="GetOwnerGroups?email=${sessionScope.user.getEmail()}">Your groups</a></li>
                                        <li><a href="ViewProfile?email=${sessionScope.user.getEmail()}">View profile</a></li>
                                            <c:choose>
                                                <c:when test="${sessionScope.user.getIsModerator() == true}">
                                                <li><a href="#moderator">Switch to moderator</a></li>
                                                </c:when>
                                            </c:choose>
                                        <li class="divider"></li>
                                        <li><a href="Logout">Logout</a></li>
                                    </ul>
                                </li>
                            </ul>

                            <div id="addPost" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Add Post</h4>
                                        </div>
                                        <div class="modal-body">
                                            <form role="form" action="AddPost?groupId=${groupPage.getGroupId()}" method="post">
                                                <div class="form-group"> 
                                                    <label>Message</label>
                                                    <textarea class="form-control" name="message" placeholder="Text input" rows="3"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputFile">File input</label>
                                                    <input type="file" name="avatar">
                                                </div>
                                                <button type="submit" class="btn btn-default">Send</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:otherwise>

                    </c:choose>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div style="width:80%; margin:0 auto;">
            <h1>${groupPage.getGroupName()}</h1>
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
                    <th class="col-sm-2" > ${(groupPage.getAdminUsername())} </th>
                    <th> Message</th>
                    <tr>
                        <td><img src="Avatars/${imgUrlAdmin}" class="img-thumbnail" style="height: 120px;width: 110px"></td>
                        <td>${groupPage.getGroupName()} was created at  ${groupPage.getCreationDate()}</td>
                    </tr>
                </table>
            </div>
            <div id="forgetPassword" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Password recovery</h4>
                        </div>
                        <div class="modal-body">
                            <p> Insert your email and your new password</p>
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
