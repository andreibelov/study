<%@ page contentType="text/html;charset=UTF-8" language="java"
%><!DOCTYPE html>
<html>
<head>
    <title>JSON example</title>
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/static/css/chat.css" />
</head>
<body>
<div class="main">
    <div id="demo">
        <table>
            <tbody>
            <tr><td><h2>JSON simple chat</h2></td><td>Beta</td></tr>
            </tbody>
        </table>
    </div>
    <div id="content">
        <div id="chat"><dl id="msgs"></dl></div>
        <div id="input">
            <label for="msg"></label>
            <textarea id="msg" name="msg" rows="5" cols="50">Type your message here</textarea>
            <div class="buttons">
                <span>(press Ctrl+Enter to send message)</span>
                <button id="send" type="button">Send</button>
                <button id="open" type="button">Open</button>
                <button id="close" type="button">Close</button>
            </div>
        </div>
    </div>
    <div id="sidebar">
        <ol id="fellows">
            <li>Coffee</li>
            <li>Tea</li>
            <li>Milk</li>
        </ol>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/chat.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${pageContext.request.contextPath}/static/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>