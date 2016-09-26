<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.request.contextPath}/static/favicon.ico">

    <title>${requestScope.pageTitle}</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${pageContext.request.contextPath}/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="${pageContext.request.contextPath}/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${pageContext.request.contextPath}/static/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="wrapper">
    <!-- Fixed navbar -->
    <jsp:include page="/WEB-INF/include/navbar.jsp" />
    <!-- end Fixed navbar -->
    <div class="container-fluid">
        <!-- Side Menu Items -->
        <jsp:include page="/WEB-INF/include/sidebar.jsp" />
        <!-- ./Side Menu Items -->
        <!-- Main content -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="jumbotron">
                <h1 data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Hello, world!</h1>
                <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
                <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true"><a data-medium-element="true" data-placeholder="" class="btn btn-primary btn-lg editable" contenteditable="true">Learn more</a></p>
            </div>
            <div class="well">
                Look, I'm in a well!
            </div>
            <ol class="breadcrumb">
                <li><a data-medium-element="true" data-placeholder="" class="editable" href="#" contenteditable="true">Home</a></li>
                <li><a data-medium-element="true" data-placeholder="" class="editable" href="#" contenteditable="true">Library</a></li>
                <li class="active">Data</li>
            </ol>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${pageContext.request.contextPath}/static/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
