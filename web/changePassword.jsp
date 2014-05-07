<%-- 
    Document   : changePassword.jsp
    Created on : May 5, 2014, 3:48:15 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change your password</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${errorMessage == 'Request out of time'}">
                <h1>Request out of time<h1>
            </c:when>
            <c:when test="${correctRequest == true}">
                <h1>Password changed succesfully</h1>
            </c:when>
            <c:when test="${correctRequest == false}">
                <h1>Request not found</h1>
            </c:when>
            <c:otherwise>
                <h1> Illegal use </h1>
            </c:otherwise>
        </c:choose>        
    </body>
</html>
