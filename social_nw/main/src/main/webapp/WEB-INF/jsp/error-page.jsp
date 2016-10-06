<%@page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@
        taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.request.contextPath}/static/favicon.ico">

    <title>Error</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link xmlns="http://www.w3.org/1999/xhtml" rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/error-page.css" type="text/css" media="all" />
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
<div class="wrapper">
    <div xmlns="http://www.w3.org/1999/xhtml" id="errorPageContainer" class="container">
        <div class="row">
            <div class="hidden-sm col-md-3"></div>
            <div class="col-xs-12 col-md-6 main">
                <!-- Error Title -->
                <div class="title">
                    <h1 class="title-text">Something went wrong...</h1>
                </div>

                <!-- LONG CONTENT (the section most likely to require scrolling) -->
                <div id="errorLongContent">

                    <!-- Short Description -->
                    <div id="errorShortDesc">
                        <p id="errorShortDescText">An exception has occurred during your request</p>
                    </div>

                    <!-- Long Description (Note: See netError.dtd for used XHTML tags) -->
                    <div id="errorLongDesc">
                        <ul xmlns="http://www.w3.org/1999/xhtml">
                            <li><b>Error:</b> ${pageContext.exception}</li>
                            <li><b>Requested URI: </b>${pageContext.errorData.requestURI}</li>
                            <li><b>Status code: </b>${pageContext.errorData.statusCode}</li>
                            <li><b>Stack trace: </b>
                                <c:forEach var="trace"
                                           items="${pageContext.exception.stackTrace}">
                                    <p>${trace}</p>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>

                    <div id="learnMoreContainer">
                        <p><a href="https://support.mozilla.org/kb/what-does-your-connection-is-not-secure-mean" id="learnMoreLink" target="new">Learn more…</a></p>
                    </div>
                </div>

                <div id="netErrorButtonContainer" class="button-container">
                    <button id="returnButton" autocomplete="off" autofocus="true" class="btn btn-primary">Go Back</button>
                </div>

                <script>
                    // Only do autofocus if we're the toplevel frame; otherwise we
                    // don't want to call attention to ourselves!  The key part is
                    // that autofocus happens on insertion into the tree, so we
                    // can remove the button, add @autofocus, and reinsert the
                    // button.
                    if (window.top == window) {
                        var button = document.getElementById("errorTryAgain");
                        var parent = button.parentNode;
                        button.remove();
                        button.setAttribute("autofocus", "true");
                        parent.appendChild(button);
                    }
                </script>
            </div>
            <div class="hidden-sm col-md-3"></div>
        </div>
    </div>
</div>
<script xmlns="http://www.w3.org/1999/xhtml" type="application/javascript">initPage();</script>
</body>
</html>