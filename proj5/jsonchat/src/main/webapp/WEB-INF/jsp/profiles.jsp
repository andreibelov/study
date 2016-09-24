<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-responsive">
    <form action="${pageContext.request.contextPath}/profile" method="post" id="profileForm" role="form" >
        <input type="hidden" id="idEmployee" name="idEmployee">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty requestScope.profileList}">
                <table  class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Last name</td>
                        <td>Birth date</td>
                        <td>Country</td>
                        <td>City</td>
                        <td>E-mail</td>
                        <td></td>
                    </tr>
                    </thead>
                    <c:forEach var="profile" items="${requestScope.profileList}">
                        <c:set var="classSucess" value=""/>
                        <c:if test ="${requestScope.idUserProfile == profile.id}">
                            <c:set var="classSucess" value="info"/>
                        </c:if>
                        <tr class="${classSucess}">
                            <td>
                                <a href="${pageContext.request.contextPath}/profile?idUserProfile=${profile.id}&searchAction=searchById">${profile.id}</a>
                            </td>
                            <td>${profile.name}</td>
                            <td>${profile.lastName}</td>
                            <td>${profile.birthDate}</td>
                            <td>${profile.country}</td>
                            <td>${profile.city}</td>
                            <td>${profile.email}</td>
                            <td><a href="#" id="remove"
                                   onclick="document.getElementById('action').value = 'remove';document.getElementById('idEmployee').value = '${profile.id}';

                                           document.getElementById('profileForm').submit();">
                                <span class="glyphicon glyphicon-trash"></span>
                            </a>

                            </td>
                        </tr>
                    </c:forEach>
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