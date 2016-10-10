<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="list-group">
    <a href="#" class="list-group-item active">Link 0</a>
    <c:forEach begin="1" end="10" varStatus="loop">
        <a href="#" class="list-group-item">Link: ${loop.current}</a>
    </c:forEach>
</div>