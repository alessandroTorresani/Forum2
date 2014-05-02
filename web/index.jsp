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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
    </head>
    <body>  
        <h1>Forum</h1>
        <c:if test="${sessionScope.user != null}">
            <c:out value ="Hi ${sessionScope.user.getUsername()},"/>
            <c:if test="${sessionScope.user.getLastLogin() == null}">
                <c:out value="this is your first login"/>
            </c:if>
            <c:if test="${sessionScope.user.getLastLogin() != null}">
                <c:out value="you was here: ${sessionScope.user.getLastLogin()}"/>
            </c:if>
        </c:if>
        <br>
        <br>
        <form action ="registration.jsp" method ="get">
            <input type="submit" value ="Sign up"/>
        </form>
        <br>
        <form action="Login" method="post">
            <input type="text" name="email"/>
            <input type="password" name="password"/>
            <input type="submit" value="Sign in"/>
        </form>
        <br>
        <form action ="Logout" method ="get">
            <input type="submit" value ="Logout"/>
        </form>
        <br>
        <c:forEach items="${publicGroups}" var="group">
            ${group.getGroupName()} ${group.getGroupId()} ${group.getAdminId()} ${group.getCreationDate()} ${group.isIsClosed()} ${group.isIsPrivate()} <br>
        </c:forEach>
        
    </body>
</html>
