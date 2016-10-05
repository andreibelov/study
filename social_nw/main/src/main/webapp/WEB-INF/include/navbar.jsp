<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><nav class="navbar navbar-inverse navbar-fixed-top">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#navbar-top" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home" style="padding-right: 7px;">
            <strong>${applicationScope.projectName}</strong>
        </a>
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home" style="padding: 0;">
            <img src="${pageContext.request.contextPath}/static/img/VaultTecLogo.png"
                 class="img-responsive" alt="VAULT-TEC" style="padding: 7px 0; height: 100%">
        </a>
    </div>
    <!-- Top Menu Items -->
    <div id="navbar-top" class="navbar-collapse collapse">
        <ul class="nav navbar-right top-nav">
            <li><a href="${pageContext.request.contextPath}/admin">Dashboard</a></li>
            <li><a href="#">Settings</a></li>
            <li class="dropdown">
                <c:if test="${not empty pageContext.request.session.getAttribute('user')}">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user">
                    </i><m:fullName /><b class="caret"></b></a>
                </c:if>
                <ul class="dropdown-menu">
                    <li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/inbox"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li>
                    <li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a></li>
                    <li class="divider"></li>
                    <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>
                </ul>
            </li>
            <li><a href="#">Help</a></li>
        </ul>
        <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
        </form>
    </div>
</nav>
