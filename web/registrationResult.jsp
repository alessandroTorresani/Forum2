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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Result</title>
    </head>
    <body>
        <h1>
            <c:out value="${Result}"/>
        </h1>
        <% String result = (String) request.getAttribute("Result");%>
        <c:choose>
            <c:when test='${Result != "Your registration was successful"}'>
                 <form action ="registration.jsp" method ="get">
                <input type="submit" value ="Retry"/>
            </form>
            </c:when>
            <c:otherwise>
                <form action ="index.jsp" method ="get">
                <input type="submit" value ="Return to home"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>
