<%-- 
    Document   : forgetPassword
    Created on : May 5, 2014, 2:52:16 PM
    Author     : Alessandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot password</title>
    </head>
    <body>
        <h1>Insert your email, you have 90 seconds to change it</h1>
        <form action="ForgotPassword" method="post">
            <input type="text" name="email"/>
            <input type ="submit" value="Change"/>
        </form>
    </body>
</html>
