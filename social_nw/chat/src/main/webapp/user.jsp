<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Add new user</title>
</head>
<body>
<form method="POST" action='users' name="frmAddUser">
    <label for="userid" >User ID : </label>
    <input type="text" readonly="readonly" id="userid" name="userid"
                     value="<c:out value="${requestScope.user.userid}" />" /> <br />
    <label for="firstName" >First Name : </label>
    <input type="text" name="firstName" id="firstName"
        value="<c:out value="${requestScope.user.firstName}" />" /> <br />
    <label for="lastName" >Last Name : </label>
    <input type="text" name="lastName" id="lastName"
        value="<c:out value="${requestScope.user.lastName}" />" /> <br />
    <label for="dob" >DOB : </label>
    <input type="text" name="dob" id="dob"
        value="<fmt:formatDate pattern="MM/dd/yyyy" value="${requestScope.user.dob}" />" /> <br />
    <label for="email" >Email : </label>
    <input type="text" name="email" id="email"
                   value="<c:out value="${requestScope.user.email}" />" /> <br />
    <label for="pass" >Password : </label>
    <input type="password" name="password" id="pass" value="" /> <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>