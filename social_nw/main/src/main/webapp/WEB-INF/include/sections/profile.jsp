<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="m" uri="/WEB-INF/taglib.tld"
%><jsp:useBean id="date" class="java.util.Date" />
<c:set var="number0" value="${0}"/>
<c:set var="number1" value="${1}"/>
<c:set var="number2" value="${2}"/>
<c:set var="number3" value="${3}"/>
<c:set var="number4" value="${4}"/>
<c:set var="number5" value="${5}"/>
<c:set var="number6" value="${6}"/>
<c:set var="number7" value="${7}"/>

<c:choose>
    <c:when test="${number3 eq requestScope.friendStatus || number2 eq requestScope.friendStatus || number7 eq requestScope.friendStatus}">
        <div class="panel panel-default">
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
                        <m:button testId="${profile.id}"/>
                        <button id="sendMessage" class="btn btn-md btn-default btn-block" data-target="${requestScope.profile.id}">Send a message</button>
                        <button class="btn btn-md btn-link blockUser btn-block" data-target="${requestScope.profile.id}">Block user</button>
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
    </c:when>
    <c:when test="${number0 eq requestScope.friendStatus || number1 eq requestScope.friendStatus || number4 eq requestScope.friendStatus}">
        <div class="panel panel-default">
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
                        <m:button testId="${profile.id}"/>
                        <button id="sendMessage" class="btn btn-md btn-default btn-block" data-target="${requestScope.profile.id}">Send a message</button>
                        <button class="btn btn-md btn-link blockUser btn-block" data-target="${requestScope.profile.id}">Block user</button>
                    </div>
                </div>
                <div class="col-xs-7 col-sm-7 fields">
                    <dl>
                        <dt>First name</dt>
                        <dd>${profile.firstName}</dd>
                        <dt>Last name</dt>
                        <dd>${profile.lastName}</dd>
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
    </c:when>
    <c:otherwise>
        <div class="alert alert-danger alert-dismissible" role="alert">
            <span class="close" data-dismiss="alert">&times;</span>
            You have been blocked by this user =(
        </div>
    </c:otherwise>
</c:choose>
