<%-- 
    Document   : viewProfile
    Created on : May 13, 2014, 3:25:50 PM
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
        <title>View profile</title>
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
                        <li><a data-toggle="modal" data-target="#changeAvatar"><span class="glyphicon glyphicon-th-list"></span><b> Change avatar</b></a></li>
                        <li><a data-toggle="modal" data-target="#changePassword"><span class="glyphicon glyphicon-th-list"></span><b> Change password</b></a></li>
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
            <c:choose>
                <c:when test="${param.changePassword=='error'}">
                    <div class="alert alert-danger">You inserted a non valid password, or passwords don't coincide</div>
                </c:when>
                <c:when test="${param.changePassword=='success'}">
                    <div class="alert alert-success">Password changed successfully</div>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${param.changeAvatar=='error-filesize'}">
                    <div class="alert alert-danger">The image that you uploaded was to big. The limit is 10 MB</div>
                </c:when>
                <c:when test="${param.changeAvatar=='error-filetype'}">
                    <div class="alert alert-danger">You did not upload an image, please retry</div>
                </c:when>
                <c:when test="${param.changeAvatar=='error-no-file'}">
                    <div class="alert alert-danger">Please select an image to change the avatar</div>
                </c:when>
            </c:choose>

            <h1>Your Profile</h1>
            <div style="width: 40%; float:left;">
                <img src="Avatars/${imgUrl}" class="img-thumbnail" style="height: 100%;width: 100%">
            </div>
            <br>
            <div style="width:55%; float:right">
                <div style="width:40%">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Username</h3>
                        </div>
                        <div class="panel-body">
                            ${sessionScope.user.getUsername()}

                        </div>
                    </div>
                </div>
                <br>
                <div style="width:40%">

                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Email</h3>
                        </div>
                        <div class="panel-body">
                            ${sessionScope.user.getEmail()}
                        </div>
                    </div>
                </div>

            </div>
        </div>


        <div id="changeAvatar" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change avatar</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" action="ChangeAvatar?email=${sessionScope.user.getEmail()}" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="exampleInputFile">File input</label>
                                <input type="file" name="avatar">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="changePassword" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Change password</h4>
                    </div>
                    <div class="modal-body">
                        <form role="form" action="ChangePassword?email=${sessionScope.user.getEmail()}" method="post">
                            <div class="form-group">
                                <label for="currentPassword1">Current password</label>
                                <input type="password" class="form-control" id="password1" placeholder="Password" name="currentPassword">
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
