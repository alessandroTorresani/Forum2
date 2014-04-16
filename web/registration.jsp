<%-- 
    Document   : registration
    Created on : Apr 14, 2014, 2:13:27 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Registration page</h1>
        <form action ="Registration" method ="POST" enctype="multipart/form-data">
            Username: <input type="text" name="username"/>
            Email: <input type="text" name="email1"/>
            Confirm-email: <input type="text" name="email2"/>
            Password: <input type="password" name="password1"/>
            Confirm-password: <input type="password" name="password2"/>
            Avatar: <input type="file" name="avatar" size="50" />
            <input type="submit" value ="SUBMIT"/>
        </form>
    </body>
</html>
