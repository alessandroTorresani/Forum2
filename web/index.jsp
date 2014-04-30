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
        <% HttpSession servletSession = request.getSession();
               User user = (User) servletSession.getAttribute("user");
               if (user != null){
                   %> <p> Hi <%=user.getUsername() + " "%><%if(user.getLastLogin() == null){
                       %> <c:out value=", this is you first login" /><% }else {  %><%=user.getLastLogin()%><%
                   }
                   %> </p> <%
               }%>
        <form action ="registration.jsp" method ="get">
            <input type="submit" value ="Sign up"/>
        </form>
        <form action="Login" method="post">
            <input type="text" name="email"/>
            <input type="password" name="password"/>
            <input type="submit" value="Sign in"/>
        </form>
        <form action ="Logout" method ="get">
            <input type="submit" value ="Logout"/>
        </form>
        <% List <Group> publicGroups = (List<Group>)request.getAttribute("publicGroups"); 
            for (int x = 0; x < publicGroups.size(); x++){
        %> <p> <%= publicGroups.get(x).getGroupName()%> <%=publicGroups.get(x).getGroupId()%>  <%=publicGroups.get(x).getAdminId()%>  <%=publicGroups.get(x).getCreationDate()%> <%=publicGroups.get(x).isIsClosed()%> <%=publicGroups.get(x).isIsPrivate() %> </p><%
            }
        %>
    </body>
</html>
