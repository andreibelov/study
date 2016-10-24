<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${not empty requestScope.profileList}">
        <ul class="media-list">
            <c:forEach var="profile" items="${requestScope.profileList}">
                <li class="media">
                    <a class="media-left" href="${pageContext.request.contextPath}/profile?id=${profile.id}">
                        <img style="width: 80px; height: 80px;" src="${pageContext.request.contextPath}/static/img/60c76697999bc703bacc61a9b48314ce.png">
                    </a>
                    <div class="media-body">
                        <a class="media-left" href="${pageContext.request.contextPath}/profile?id=${profile.id}">
                            <h4 class="media-heading">${profile.firstName} ${profile.lastName}</h4>
                        </a>
                        <dl style="margin-bottom: 0;">
                            <dt>Country</dt>
                            <dd>${profile.country.name}</dd>
                            <dt>City / town</dt>
                            <dd>${profile.city}</dd>
                        </dl>
                    </div>
                    <div class="media-right">
                        <m:button testId="${profile.id}"/>
                        <button id="sendMessage" class="btn btn-md btn-default btn-block" data-target="${profile.id}">Send a message</button>
                    </div>
                </li>
                <hr />
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <div class="alert alert-info">
            No people found matching your search criteria. <a href="${pageContext.request.contextPath}/profiles">Try search from all people?</a>
        </div>
    </c:otherwise>
</c:choose>