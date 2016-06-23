<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ru-RU">
<head>
  <title>Webservlet example</title>
  <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/static/css/main.css" />
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/core.js"></script>
</head>
<body>
<div id="main" class="main">
    <div id="demo">
        <h1>Please, provide your login credentials</h1>
    </div>
    <div id="login" class="login">
    <form name="loginform" id="loginform" action="login" method="post">
        <label for="user_login">Username:</label>
        <br>
            <input name="login" id="user_login" type="text" >
        <br>
        <label for="user_pass">Login:</label>
        <br>
                <input name="pass" id="user_pass" type="password">
        <br>
        <input type="submit" value="Login">
    </form></div>
</div>
</body>
</html>