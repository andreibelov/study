<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty requestScope.message}">
    <div class="alert alert-danger" role="alert">
        <strong>${requestScope.message}</strong>
    </div>
</c:if>
<br />
<form id="editor" action="${pageContext.request.contextPath}/signup" method="post"  role="form" data-toggle="validator" >
    <c:if test ="${empty requestScope.action}">
        <c:set var="action" value="add"/>
    </c:if>

    <input type="hidden" id="action" name="action" value="${action}">
    <input type="hidden" id="userId" name="userId" value="${user.id}">
    <div class="form-group col-xs-4">

        <label for="userid" class="control-label col-xs-4">User ID: ${user.id}</label>

        <label for="login" class="control-label col-xs-4">Login:</label>
        <input type="text" name="login" id="login" class="form-control" value="${user.login}" required="true"/>

        <label for="email" class="control-label col-xs-4">E-mail:</label>
        <input type="text" name="email" id="email" class="form-control" value="${user.email}" placeholder="smith@aol.com" required="true"/>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" autocomplete="off" id="inputPassword" class="form-control" placeholder="Password" required />

        <c:choose>
            <c:when test="${user.accessLevel == 0}">
                <c:set value="selected" var="selPos0"/>
            </c:when>
            <c:when test="${user.accessLevel == 1}">
                <c:set value="selected" var="selPos1"/>
            </c:when>
            <c:when test="${user.accessLevel == 2}">
                <c:set value="selected" var="selPos2"/>
            </c:when>
            <c:otherwise>
                <c:set value="selected" var="selPos3"/>
            </c:otherwise>
        </c:choose>

        <label for="accessLevel" class="control-label col-xs-4">Access Level:</label>
        <select class="form-control" id="accessLevel" name="accessLevel">
            <option selected ${selPos3}>Select level</option>
            <option value="0" ${selPos0}>Admin</option>
            <option value="1" ${selPos1}>Moderator</option>
            <option value="2" ${selPos2}>Registered</option>
        </select>
    </div>
</form>
<br />
<button id="accept" class="btn btn-primary btn-md">Save</button>
