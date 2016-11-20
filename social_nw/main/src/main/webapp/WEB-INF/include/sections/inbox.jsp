<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><c:choose>
    <c:when test="${requestScope.messageList != null}">
        <jsp:include page="/WEB-INF/include/sections/messages.jsp" />
    </c:when>
    <c:when test="${requestScope.conversations != null}">
        <jsp:include page="/WEB-INF/include/sections/conversations.jsp" />
    </c:when>
    <c:otherwise>
        <div class="alert alert-danger alert-dismissible" role="alert">
            <span class="close" data-dismiss="alert">&times;</span>
            You have no conversations yet.
        </div>
    </c:otherwise>
</c:choose>

