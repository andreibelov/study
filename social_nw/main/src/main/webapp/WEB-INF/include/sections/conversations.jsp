<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="m" uri="/WEB-INF/taglib.tld" %>
<jsp:useBean id="date" class="java.util.Date" />

<div class="inbox">
    <c:choose>
        <c:when test="${not empty requestScope.conversations}">
            <ul class="media-list">
                <li class="media">
                    <a data-medium-element="true" class="pull-left" href="#">
                        <img src="${pageContext.request.contextPath}/static/img/vault-girl-right.png"
                             class="media-object" style="width: 50px;" alt="media">
                    </a><div class="media-body" style="padding-right: 10%;">
                    <h4 data-medium-element="true" class="media-heading">Friend request</h4>
                    <span class="text-muted">${date}</span>
                    <p data-medium-element="true" data-placeholder="" contenteditable="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulput&nbsp;&nbsp; ate at, tempus viverra turpis.</p>
                </div>
                </li>
                <hr>
                <c:forEach begin="0" end="10" varStatus="loop">
                    <li class="media">
                        <p class="text-center"><span class="text-muted">${date}</span>
                            <a href="#"><m:fullName /></a></p>
                        <div class="media-body text-right" style="padding-left: 10%;">
                            <p data-medium-element="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra.</p>
                        </div>
                        <div class="media-right" style="padding-top: 5px;">
                            <a href="#">
                                <img src="${pageContext.request.contextPath}/static/img/vault-boy-left.png"
                                     class="media-object" style="width: 50px;" alt="media">
                            </a>
                        </div>
                    </li>
                    <li class="media">
                        <div class="media-body text-right" style="padding-left: 10%; padding-right: 60px;">
                            <p data-medium-element="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra.</p>
                        </div>
                    </li>
                    <hr>
                    <li class="media">
                        <p class="text-center"><span class="text-muted">${date}</span>
                            <a href="#"> Anna Petrova </a></p>
                        <a data-medium-element="true"
                           class="pull-left medium-editor-placeholder" href="#" style="padding-top: 5px;">
                            <img src="${pageContext.request.contextPath}/static/img/vault-girl-right.png"
                                 class="media-object" style="width: 50px;" alt="media">
                        </a>
                        <div class="media-body" style="padding-right: 10%;">
                            <p data-medium-element="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulput&nbsp;&nbsp; ate at, tempus viverra turpis.</p>
                        </div>
                    </li>
                    <li class="media">
                        <div class="media-body text-left" style="padding-left: 60px; padding-right: 10%;">
                            <p data-medium-element="true">Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra.</p>
                        </div>
                    </li>
                    <hr>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <div class="alert alert-danger alert-dismissible" role="alert">
                <span class="close" data-dismiss="alert">&times;</span>
                You have no conversations yet.
            </div>
        </c:otherwise>
    </c:choose>
</div>
