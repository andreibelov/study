<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><jsp:useBean id="date" class="java.util.Date" /><div class="col-xs-12 col-md-9 inbox">
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
                <a href="#"> Andrei Belov </a></p>
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
</div>
<div id="sidebar" class="col-xs-12 col-md-3 sidebar-offcanvas">
    <div class="list-group">
        <a href="#" class="list-group-item active">Link 1</a>
        <c:forEach begin="2" end="10" varStatus="loop">
            <a href="#" class="list-group-item">Link: ${loop.current}</a>
        </c:forEach>
    </div>
</div>