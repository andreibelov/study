<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty requestScope.message}">
    <div class="alert alert-danger" role="alert">
        <strong>${requestScope.message}</strong>
    </div>
</c:if>
<br />
<form action="${pageContext.request.contextPath}/profile" method="post" id="profileForm" role="form" >
    <c:choose>
        <c:when test="${not empty requestScope.profileList}">
            <table  class="table table-striped">
                <thead>
                <tr>
                    <td>#</td>
                    <td>User ID</td>
                    <td>Name</td>
                    <td>Last name</td>
                    <td>Birth date</td>
                    <td>Country</td>
                    <td>City</td>
                    <td>E-mail</td>
                    <td></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="profile" items="${requestScope.profileList}">
                    <c:set var="classSucess" value=""/>
                    <tr class="${classSucess}"  data-target="${profile.id}">
                        <td>
                            <a href="#" id="edit">${profile.id}</a>
                        </td>
                        <td>${profile.userid}</td>
                        <td>${profile.name}</td>
                        <td>${profile.lastName}</td>
                        <td>${profile.birthDate}</td>
                        <td>${profile.country}</td>
                        <td>${profile.city}</td>
                        <td>${profile.email}</td>
                        <td><a href="#" id="remove">
                            <span class="glyphicon glyphicon-trash"></span>
                        </a>
                        </td>
                    </tr>
                </c:forEach>
                    <tr id="loadMore" class="text-center warning">
                        <td colspan="9"><h3>Load more</h3></td>
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