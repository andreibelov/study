<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="editor" action="${pageContext.request.contextPath}/profile" method="post"  role="form" data-toggle="validator" >
    <c:if test ="${empty requestScope.action}">
        <c:set var="action" value="add"/>
    </c:if>
    <input type="hidden" id="action" name="action" value="${action}">
    <input type="hidden" id="idUserProfile" name="idUserProfile" value="${requestScope.profile.id}">
    <h2>Employee</h2>
    <div class="form-group col-xs-4">
        <label for="name" class="control-label col-xs-4">Name:</label>
        <input type="text" name="name" id="name" class="form-control" value="${requestScope.profile.name}" required="true"/>

        <label for="lastName" class="control-label col-xs-4">Last name:</label>
        <input type="text" name="lastName" id="lastName" class="form-control" value="${requestScope.profile.lastName}" required="true"/>

        <label for="birthdate" class="control-label col-xs-4">Birth date</label>
        <input type="text"  pattern="^\d{2}-\d{2}-\d{4}$" name="birthDate" id="birthdate" class="form-control" value="${requestScope.profile.birthDate}" maxlength="10" placeholder="dd-MM-yyy" required="true"/>

        <label for="country" class="control-label col-xs-4">Country:</label>
        <input type="text" name="country" id="country" class="form-control" value="${requestScope.profile.country}" required="true"/>

        <label for="city" class="control-label col-xs-4">City:</label>
        <input type="text" name="city" id="city" class="form-control" value="${requestScope.profile.city}" required="true"/>

        <label for="email" class="control-label col-xs-4">E-mail:</label>
        <input type="text" name="email" id="email" class="form-control" value="${requestScope.profile.email}" placeholder="smith@aol.com" required="true"/>

    </div>
</form>
<br />
<button id="accept" class="btn btn-primary btn-md">Accept</button>
