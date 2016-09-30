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
    <label class="control-label">Select File</label>
    <input id="file-input" name="file" type="file" class="file">
    <br />
    <input type="submit" value="Upload File" />
</form>
