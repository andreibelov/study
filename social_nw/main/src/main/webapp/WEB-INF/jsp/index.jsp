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

    <c:if test="${not empty requestScope.section}">
        <title>${requestScope.section.pageTitle}</title>
    </c:if>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet">
    <c:if test="${not empty requestScope.section}">
        <link href="${pageContext.request.contextPath}/static/css/${requestScope.section.cssFile}" rel="stylesheet">
    </c:if>
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
        <div class="row">
        <c:choose>
            <c:when test="${not empty requestScope.logindata}">
                    <jsp:include page="/WEB-INF/include/${requestScope.section.jspFile}" />
            </c:when>
            <c:otherwise>
                    <!-- Side Menu Items -->
                    <div class="col-sm-3 col-md-2 sidebar">
                        <jsp:include page="/WEB-INF/include/sidebar.jsp" />
                    </div>
                    <!-- ./Side Menu Items -->

                    <!-- Main content -->
                    <div id="main-column" class="col-xs-12 col-sm-9 col-sm-offset-3 col-md-6 col-md-offset-2 main">
                        <!-- Section content -->
                        <c:if test="${not empty requestScope.section}">
                            <jsp:include page="/WEB-INF/include/sections/${requestScope.section.jspFile}" />
                        </c:if>
                        <!-- end Section content -->
                    </div><!-- end Main content -->

                    <!-- Right panel Items -->
                    <div class="hidden-xs hidden-sm col-md-4 pull-right rightbar">
                        <jsp:include page="/WEB-INF/include/rightbar.jsp" />
                    </div><!-- end Right panel Items -->
            </c:otherwise>
        </c:choose>
        </div>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<c:if test="${not empty requestScope.section}">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/${requestScope.section.jsFile}"></script>
</c:if>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${pageContext.request.contextPath}/static/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
