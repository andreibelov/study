<%@ taglib prefix="m" uri="/WEB-INF/taglib.tld"
%><div class="thumb">
    <img src="${pageContext.request.contextPath}/static/img/cool!.png"
         class="img-responsive" alt="Generic placeholder thumbnail" width="200" height="200">
    <h4><m:fullName /></h4>
    <span class="text-muted">Edit prifile</span>

</div>
<ul class="nav nav-sidebar">
    <li class="active"><a href="${pageContext.request.contextPath}/home"><i class="fa fa-fw fa-home"></i> Home<span class="sr-only">(current)</span></a></li>
    <li><a href="${pageContext.request.contextPath}/inbox"><i class="fa fa-fw fa-envelope"></i> Inbox<span class="badge">42</span></a></li>
    <li><a href="${pageContext.request.contextPath}/friends"><i class="fa fa-fw fa-users" aria-hidden="true"></i> Friends</a></li>
    <li><a href="${pageContext.request.contextPath}/music"><i class="fa fa-fw fa-music"></i> Music</a></li>
</ul>