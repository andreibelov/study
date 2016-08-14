<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"
%><!DOCTYPE html>
<html>
<head>
    <title>Ajax example</title>
    <!-- Custom styles for this template -->
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/static/css/chat.css" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="main">

    <div id="demo">
        <table>
            <tbody>
            <tr><td><h2>Ajax simple chat</h2></td><td>Beta</td></tr>
            </tbody>
        </table>
    </div>
    <div id="chat"><dl></dl></div>
    <div id="input">
        <label for="msg">Your message: (press Ctrl+Enter to send message)</label>
        <br />
        <textarea id="msg" name="msg" rows="4" cols="50">Type your message here</textarea>
    </div>
    <div class="buttons">
        <button id="send" type="button">Send</button>
        <button id="clc" type="button">Clear</button>
        <button id="attch" type="button">Attach</button>
    </div>
</div>
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/chat.js"></script>
</body>
</html>>
