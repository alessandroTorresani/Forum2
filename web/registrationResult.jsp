<%-- 
    Document   : registrationResult
    Created on : Apr 24, 2014, 3:12:35 PM
    Author     : harwin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="Style/css/bootstrap.min.css" rel="stylesheet">
        <title>Registration Result</title>
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

            <% /* <ul class="nav nav-pills">
                 <li class="active"><a href="Start">Home</a></li>
                 <c:choose>
                 <c:when test="${sessionScope.user != null}">

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
                 </c:when>
                 <c:otherwise>       
                 <li><a href="registration.jsp">Sign up</a></li>
                 <li><a href="forgetPassword.jsp">Forget password</a></li>
                 </c:otherwise>
                 </c:choose>
                 </ul>*/%>

            <h1>
                <c:out value="${Result}"/>
            </h1>   

            <c:choose>
                <c:when test='${Result != "Your registration was successful"}'>

                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">Error</h3>
                        </div>
                        <div class="panel-body">

                            <c:choose>
                                <c:when test="${usernameCheck == false}">
                                    <p>You inserted a non valid username</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${pass1Check == false}">
                                    <p>You inserted a non valid password</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${email1Check == false}">
                                    <p>You inserted a non valid email</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${equalPass == false}">
                                    <p>Passwords don't corresponding</p>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${equalEmail == false}">
                                    <p>Emails don't corresponding </p>
                                </c:when>  
                            </c:choose>
                            <br>
                            <a href="registration.jsp">
                                <BUTTON class="btn btn-danger">Retry</BUTTON>
                            </a>
                        </c:when>
                        <c:otherwise>   
                            <div class="panel panel-success">
                                <div class="panel-heading">                                    
                                    <h3 class="panel-title">Success</h3>
                                </div>
                                <div class="panel-body">
                                    <a href="Start">
                                        <BUTTON class="btn btn-success">Return home</BUTTON>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

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
