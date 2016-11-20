<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><div class="thumb">
    <img src="${pageContext.request.contextPath}/static/img/cool!.png"
         class="img-responsive" alt="Generic placeholder thumbnail" width="200" height="200">
    <h4><m:fullName doLink="true" /></h4>
    <span class="text-muted"><a href="${pageContext.request.contextPath}/profile?action=edit">Edit profile</a></span>
</div>
<ul class="nav nav-sidebar">
    <c:forEach var="entry" items="${requestScope.sections}"
    ><li ${entry.value == requestScope.section ? 'class="active"' : ''}>
        <a href="${pageContext.request.contextPath}/${entry.key}">
                ${entry.value.data}
                ${entry.value.sectionName}
                ${entry.value == requestScope.section ? '<span class="sr-only">(current)</span>' : ''}</a>
    </li>
    </c:forEach>
    <%--<li><a href="${pageContext.request.contextPath}/music"><i class="fa fa-fw fa-music"></i> Music</a></li>--%>
</ul>