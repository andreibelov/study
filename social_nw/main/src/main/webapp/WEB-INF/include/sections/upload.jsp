<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>File Upload:</h3>
<br />
<c:if test="${not empty requestScope.errorMessage}">
    <div class="alert alert-info" role="alert">
        <strong>${requestScope.errorMessage}</strong>
    </div>
    <br />
</c:if>
Select a file to upload: <br />
<form action="${pageContext.request.contextPath}/upload" method="post"
      enctype="multipart/form-data">

    <label class="btn btn-primary" for="my-file-selector">
        <input name="file" id="my-file-selector" type="file" style="display:none;" onchange="$('#upload-file-info').html($(this).val());">
        Button Text Here
    </label>
    <span class='label label-info' id="upload-file-info"></span>
    <input type="submit" value="Upload File" />
</form>
