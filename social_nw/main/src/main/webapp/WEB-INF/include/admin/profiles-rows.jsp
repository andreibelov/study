<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><c:forEach var="profile" items="${requestScope.profileList}"
    ><c:set var="classSucess" value=""
    /><tr class="${classSucess}"  data-target="${profile.id}"
          style="cursor: pointer; cursor: hand;">
    <td>
        <a href="#" id="edit">${profile.id}</a>
    </td>
    <td>${profile.firstName}</td>
    <td>${profile.lastName}</td>
    <td><fmt:formatDate value="${profile.birthDate}" pattern="dd-MM-yyyy" /></td>
    <td>${profile.country}</td>
    <td>${profile.city}</td>
    <td>${profile.email}</td>
    <td><a href="#" id="remove">
        <span class="glyphicon glyphicon-trash"></span>
    </a>
    </td>
</tr></c:forEach>