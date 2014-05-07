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
        <h1>Insert your email and your new password</h1>
        <h1>If you visit the link that we will send to your email in 90 seconds, your modification will be applied </h1>

        <form action="ForgotPassword" method="post">
            Email: <input type="text" name="email"/>
            New password: <input type="password" name="password1"/>
            Repeat password: <input type="password" name="password2"/>
            <input type ="submit" value="Change"/>
        </form>
    </body>
</html>
