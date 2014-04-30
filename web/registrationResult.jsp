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
                  
                 <% /*
                <c:out value="${usernameCheck}"/>
                <c:out value="${pass1Check}"/>
                <c:out value="${pass2Check}"/>
                <c:out value="${email1Check}"/>
                <c:out value="${email2Check}"/>
                <c:out value="${equalPass}"/>
                <c:out value="${equalEmail}"/>*/
                 %>
                 
            </c:when>
            <c:otherwise>
                <form action ="Start" method ="get">
                    <input type="submit" value ="Return to home"/>
                </c:otherwise>
            </c:choose>
    </body>
</html>
