<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><c:if test="${!empty requestScope.message}">
    <div class="alert alert-danger" role="alert">
        <strong>${requestScope.message}</strong>
    </div>
    <br />
</c:if>
<div class="row profile-form">
    <div class="col-xs-9 col-sm-8">
        <form id="editor" class="form-horizontal"
              action="${pageContext.request.contextPath}/profile" method="post"  role="form" data-toggle="validator">
            <c:if test ="${empty requestScope.action}">
                <c:set var="action" value="add"/>
            </c:if>
            <input type="hidden" id="action" name="action" value="${action}">
            <input type="hidden" id="userProfileId" name="userProfileId" value="${profile.id}">
            <div class="form-group">
                <label for="userid" class="control-label col-xs-4">User ID:</label>
                <div class="col-xs-8">
                    <input type="text" name="userid" id="userid" class="form-control input-sm" value="${profile.userid}" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="control-label col-xs-4">Name:</label>
                <div class="col-xs-8">
                    <input type="text" name="name" id="name" class="form-control input-sm" value="${profile.name}" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName" class="control-label col-xs-4">Last name:</label>
                <div class="col-xs-8">
                    <input type="text" name="lastName" id="lastName" class="form-control input-sm" value="${profile.lastName}" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="birthdate" class="control-label col-xs-4">Birth date</label>
                <div class="col-xs-8">
                    <input type="text"  pattern="^\d{2}-\d{2}-\d{4}$" name="birthDate" id="birthdate"
                           class="form-control input-sm date" value="<fmt:formatDate value="${requestScope.profile.birthDate}" pattern="dd-mm-yyyy" />"
                           maxlength="10" placeholder="dd-mm-yyyy" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="country" class="control-label col-xs-4">Country:</label>
                <div class="col-xs-8">
                    <input type="text" name="country" id="country" class="form-control input-sm" value="${profile.country}" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="city" class="control-label col-xs-4">City:</label>
                <div class="col-xs-8">
                    <input type="text" name="city" id="city" class="form-control input-sm" value="${profile.city}" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="control-label col-xs-4">E-mail:</label>
                <div class="col-xs-8">
                    <input type="text" name="email" id="email" class="form-control input-sm" value="${profile.email}" placeholder="smith@aol.com" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <label for="status" class="control-label col-xs-4">Status:</label>
                <div class="col-xs-8">
                    <input type="text" name="status" id="status" class="form-control input-sm" value="${profile.status}" placeholder="smith to share" required="true"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-4">
                    <button id="cancel" type="button" class="btn btn-danger btn-block">Cancel</button>
                </div>
                <!-- Button -->
                <div class="col-xs-8">
                    <button type="submit" class="btn btn-md btn-primary btn-block">Save</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-xs-3 col-sm-4">
        <div class="form-group">
            <button class="btn btn-md btn-default btn-block">My photo</button>
        </div>
        <img class="img-responsive" alt="100%x200" data-holder-rendered="true"
             src="${pageContext.request.contextPath}/static/img/MysteriousStranger.png">
        <div class="caption text-center">
            <h4>Misterios Stranger</h4>
        </div>

    </div>

</div>
