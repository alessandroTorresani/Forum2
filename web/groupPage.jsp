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
                            <ul class="nav navbar-nav navbar-right">
                                <li><a  data-toggle="modal" data-target="#forgetPassword">Forgot password</a></li>
                                <li><a  href="registration.jsp">Sign up</a></li>
                            </ul>

                        </c:when>
                        <c:otherwise>

                            <c:choose>
                                <c:when test="${(isSubscribed == true) && (groupPage.isIsClosed() == false)}">
                                    <ul class="nav navbar-nav navbar-left">
                                        <li><a data-toggle="modal" data-target="#addPost"><span class="glyphicon glyphicon-comment"></span><b> Add post</b></a></li>
                                    </ul>
                                </c:when>
                            </c:choose>

                            <c:choose>
                                <c:when test="${sessionScope.user.isModeratorMode() && groupPage.isIsClosed() == false}">
                                    <ul class="nav navbar-nav navbar-left">
                                        <li><a href="CloseGroup?email=${sessionScope.user.getEmail()}&groupId=${groupPage.getGroupId()}"><span class="glyphicon glyphicon-off"></span><b> Close group</b></a></li>
                                    </ul>
                                </c:when>
                            </c:choose>

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

                            <div id="addPost" class="modal fade">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Add Post</h4>
                                        </div>
                                        <div class="modal-body">
                                            <form role="form" action="AddPost?groupId=${groupPage.getGroupId()}" method="post" enctype="multipart/form-data">
                                                <div class="form-group"> 
                                                    <label>Message</label>
                                                    <textarea class="form-control" name="message" placeholder="Text input" rows="3"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputFile">File input</label>
                                                    <input type="file" name="file1">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputFile">File input</label>
                                                    <input type="file" name="file2">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputFile">File input</label>
                                                    <input type="file" name="file3">
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

            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <c:choose>
                        <c:when test="${param.addPost=='error-notSubscribed'}">
                            <div class="alert alert-danger">You are not subscribed in this group</div>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${param.result == 'fileSizeError'}">
                                    <div class="alert alert-danger">File size is too big. The limit is 10 MB for each file</div>
                                </c:when>
                                <c:when test="${param.addPost == 'error-messageLenght'}">
                                    <div class="alert alert-danger">The post content is too long, max 4000 characters</div>
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>

            <c:choose>
                <c:when test="${groupPage.isIsClosed() == true}">
                    <div class="panel panel-default">
                        <!-- Table -->
                        <table class="table table-condensed table-striped">
                            <th class="col-sm-2" > ${(groupPage.getAdminUsername())} </th>
                            <th> Message</th>
                            <tr class="danger">
                                <td><img src="Avatars/${imgUrlAdmin}" class="img-thumbnail" style="height: 120px;width: 110px"></td>
                                <td>${groupPage.getGroupName()} is CLOSED  </td>
                            </tr>
                        </table>
                    </div>
                </c:when>
            </c:choose>
            <c:forEach items="${posts}" var="post">
                <div class="panel panel-default">
                    <!-- Table -->
                    <table class="table table-condensed table-striped">
                        <th class="col-sm-2" > ${post.getUsername()} </th>
                        <th> Message</th>

                        <tr>
                            <td><img src="Avatars/${post.getImgUrl()}" class="img-thumbnail" style="height: 120px;width: 110px"></td>
                            <td>${post.getMessage()}
                                <br>

                                <c:forEach items="${post.fileUrls}" var="fileUrl">
                                    <a href="Files/${fileUrl}?groupId=${groupPage.getGroupId()}" target="_blank"><span class="glyphicon glyphicon-paperclip"></span></a>${fileUrl}
                                    </c:forEach>
                                <p style="font-size: 10px">Message posted at ${post.getCreationDate()}</p>
                            </td>
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
