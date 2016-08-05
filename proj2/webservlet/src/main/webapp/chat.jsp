<%@ page contentType="text/html;charset=UTF-8" language="java"
%><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Chat page</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/chat.css" />

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/font/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Fixed navbar -->
<jsp:include page="/WEB-INF/jsp/navbar.jsp" />
<!-- end Fixed navbar -->
<div class="main">
    <div id="demo">
        <table>
            <tbody>
            <tr><td><h2>Java simple chat</h2></td><td>Beta</td></tr>
            </tbody>
        </table>
    </div>
    <div id="chat"><dl id="list"></dl></div>
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
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/chat.js"></script>
</body>
</html>
