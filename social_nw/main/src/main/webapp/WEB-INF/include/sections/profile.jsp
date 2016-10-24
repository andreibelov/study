<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><jsp:useBean id="date" class="java.util.Date" /><div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><i class="fa fa-circle" aria-hidden="true"></i> Online</h3>
    </div>
    <div class="panel-body">
        <div class="row row-eq-height">
            <div class="col-xs-5 col-sm-5">
            <img class="img-responsive" alt="100%x200" data-holder-rendered="true" src="${pageContext.request.contextPath}/static/img/MysteriousStranger.png">
            <div class="caption text-center">
                <h4>${profile.login}</h4>
            </div>

            <div class="form-group">
                <!-- Button -->
                <button class="btn btn-md btn-primary btn-block">Add to friends</button>
                <button class="btn btn-md btn-default btn-block">Send a message</button>
            </div>
        </div>
            <div class="col-xs-7 col-sm-7 fields">
                <dl>
                    <dt>First name</dt>
                    <dd>${profile.firstName}</dd>
                    <dt>Last name</dt>
                    <dd>${profile.lastName}</dd>
                    <dt>Birth date</dt>
                    <dd><fmt:formatDate value="${requestScope.profile.birthDate}" pattern="dd-mm-yyyy" /></dd>
                    <dt>Gender</dt>
                    <dd>${profile.sex.sex}</dd>
                    <dt>Mobile No.</dt>
                    <dd>${profile.phone}</dd>
                    <dt>Country</dt>
                    <dd>${profile.country.name}</dd>
                    <dt>City / Town</dt>
                    <dd>${profile.city}</dd>
                </dl>
            </div>
        </div>
    </div>

</div>
<jsp:include page="/WEB-INF/include/sections/wall.jsp" />