<%-- 
    Document   : editGroup
    Created on : May 19, 2014, 4:19:17 PM
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
        <title>Edit group</title>
    </head>
    <body>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src='Style/js/bootstrap.min.js'></script>

        <h1>Hello World!</h1>
        <form role="form" action="EditGroup?email=${sessionScope.user.getEmail()}&groupId=${group.getGroupId()}" method="post">
            <div class="form-group">
                <label for="groupName">Group name</label>
                <input type="text" class="form-control" id="groupName" placeholder="${group.getGroupName()}" name="groupName" value="${group.getGroupName()}">
            </div>
            <c:choose>
                <c:when test="${group.isIsPrivate() == false}">
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
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="public">
                                Public
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="private"checked>
                                Private
                            </label>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            <br>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </body>
</html>
