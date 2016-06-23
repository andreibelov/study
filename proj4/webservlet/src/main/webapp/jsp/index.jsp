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
<%
  //allow access only if session exists
  String user = (String) session.getAttribute("user");
  String userName = null;
  String sessionID = null;
  Cookie[] cookies = request.getCookies();
  if(cookies !=null){
    for(Cookie cookie : cookies){
      if(cookie.getName().equals("user")) userName = cookie.getValue();
      if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
    }
  }
%>
<div class="main">
  <div id="demo">
    <table>
      <tbody>
      <tr><td><h2>Webservlet example</h2></td><td>Beta</td></tr>
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
</body>
</html>