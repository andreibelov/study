<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="list-group">
    <c:choose>
        <c:when test="${not empty requestScope.linkMap}">
            <c:forEach var="entry" items="${requestScope.linkMap}">
                <a ${entry.key == requestScope.link ? 'class="list-group-item active"' : 'class="list-group-item"'}
                        href="${pageContext.request.contextPath}/${entry.value}">${entry.key}</a>
            </c:forEach>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</div>