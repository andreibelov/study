<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><div class="thumb">
    <img class="img-responsive" alt="Generic placeholder thumbnail"
         src="${pageContext.request.contextPath}/static/img/Fo4_Hacker.png" width="200"></div>
    <ul class="nav nav-sidebar">
    <c:forEach var="entry" items="${requestScope.sections}"
    ><li ${entry.value == requestScope.section ? 'class="active"' : ''}>
        <a href="${pageContext.request.contextPath}/admin?sec=${entry.key}">${entry.value.sectionName}
        ${entry.value == requestScope.section ? '<span class="sr-only">(current)</span>' : ''}</a>
    </li>
    </c:forEach>
</ul>