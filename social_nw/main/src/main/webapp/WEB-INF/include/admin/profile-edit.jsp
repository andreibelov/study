<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><c:if test="${!empty requestScope.message}">
    <div class="alert alert-danger" role="alert">
        <strong>${requestScope.message}</strong>
    </div>
    <br />
</c:if>
<div class="col-sm-12 col-md-8">
    <form id="editor" class="form-horizontal"
          action="${pageContext.request.contextPath}/profile" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty requestScope.action}">
            <c:set var="action" value="add"/>
        </c:if>

        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="userProfileId" name="userProfileId" value="${profile.id}">
        <div class="form-group">
            <label for="userid" class="control-label col-xs-4">User ID:</label>
            <div class="col-xs-8">
                <input type="text" name="userid" id="userid" class="form-control" value="${profile.userid}" readonly/>
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="control-label col-xs-4">Name:</label>
            <div class="col-xs-8">
            <input type="text" name="name" id="name" class="form-control" value="${profile.name}" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="lastName" class="control-label col-xs-4">Last name:</label>
            <div class="col-xs-8">
            <input type="text" name="lastName" id="lastName" class="form-control" value="${profile.lastName}" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="birthdate" class="control-label col-xs-4">Birth date</label>
            <div class="col-xs-8">
            <input type="text"  pattern="^\d{2}-\d{2}-\d{4}$" name="birthDate" id="birthdate"
                   class="form-control date" value="<fmt:formatDate value="${requestScope.profile.birthDate}" pattern="dd-mm-yyyy" />"
                   maxlength="10" placeholder="dd-mm-yyyy" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="country" class="control-label col-xs-4">Country:</label>
            <div class="col-xs-8">
            <input type="text" name="country" id="country" class="form-control" value="${profile.country}" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="city" class="control-label col-xs-4">City:</label>
            <div class="col-xs-8">
            <input type="text" name="city" id="city" class="form-control" value="${profile.city}" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="email" class="control-label col-xs-4">E-mail:</label>
            <div class="col-xs-8">
            <input type="text" name="email" id="email" class="form-control" value="${profile.email}" placeholder="smith@aol.com" required="true"/>
        </div>
        </div>
        <div class="form-group">
            <label for="status" class="control-label col-xs-4">Status:</label>
            <div class="col-xs-8">
            <input type="text" name="status" id="status" class="form-control" value="${profile.status}" placeholder="smith to share" required="true"/>
        </div>
        </div>
        <div class="text-right">
            <button id="accept" class="btn btn-primary btn-md" type="button">Save</button>
        </div>
    </form>
</div>
<div class="col-sm-12 col-md-4">
    <div style="min-width: 200px;" class="thumbnail">
        <img alt="100%x200" data-holder-rendered="true"
             src="${pageContext.request.contextPath}/static/img/MysteriousStranger.png" style="width: 200px;">
        <div class="caption text-center"> <h3>Misterios Stranger</h3>
            <p><a href="#" class="btn btn-primary" role="button">Button</a>
                <a href="#" class="btn btn-default" role="button">Button</a></p>
        </div>
    </div>
</div>