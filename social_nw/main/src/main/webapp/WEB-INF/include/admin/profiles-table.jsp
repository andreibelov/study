<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><div class="table-responsive"><c:if test="${not empty requestScope.message}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <span class="close" data-dismiss="alert">&times;</span>
        <strong>Warning! </strong>${requestScope.message}
    </div>
    <br />
    </c:if>
    <form action="${pageContext.request.contextPath}/profile" method="post" id="profileForm" role="form" >
        <c:choose>
            <c:when test="${not empty requestScope.profileList}">
                <table  class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>First Name</td>
                        <td>Last name</td>
                        <td>Birth date</td>
                        <td>Country</td>
                        <td>City</td>
                        <td>E-mail</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:include page="/WEB-INF/include/admin/profiles-rows.jsp" />
                    <tr id="loadMore" class="text-center" style="cursor: pointer; cursor: hand; ">
                        <td colspan="9"><span>Load more </span><b class="caret"></b></td>
                    </tr>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <br>
                <div class="alert alert-info">
                    No people found matching your search criteria
                </div>
            </c:otherwise>
        </c:choose>
    </form>
</div>