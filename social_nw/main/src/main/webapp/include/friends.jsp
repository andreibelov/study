        <!-- Main content -->
        <div class="row">
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="jumbotron">
                    <h1 data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">Hello, world!</h1>
                    <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
                    <p data-medium-element="true" data-placeholder="" class="editable" contenteditable="true"><a data-medium-element="true" data-placeholder="" class="btn btn-primary btn-lg editable" contenteditable="true">Learn more</a></p>
                </div>
                <div class="row">
                    <c:choose>
                        <c:when test="${not empty employeeList}">
                            <table  class="table table-striped">
                                <thead>
                                <tr>
                                    <td>#</td>
                                    <td>Name</td>
                                    <td>Last name</td>
                                    <td>Birth date</td>
                                    <td>Role</td>
                                    <td>Department</td>
                                    <td>E-mail</td>
                                    <td></td>
                                </tr>
                                </thead>
                                <c:forEach var="employee" items="${employeeList}">
                                    <c:set var="classSucess" value=""/>
                                    <c:if test ="${idEmployee == employee.id}">
                                        <c:set var="classSucess" value="info"/>
                                    </c:if>
                                    <tr class="${classSucess}">
                                        <td>
                                            <a href="${pageContext.request.contextPath}/employee?idEmployee=${employee.id}&searchAction=searchById">${employee.id}</a>
                                        </td>
                                        <td>${employee.name}</td>
                                        <td>${employee.lastName}</td>
                                        <td>${employee.birthDate}</td>
                                        <td>${employee.role}</td>
                                        <td>${employee.department}</td>
                                        <td>${employee.email}</td>
                                        <td><a href="#" id="remove"
                                               onclick="document.getElementById('action').value = 'remove';document.getElementById('idEmployee').value = '${employee.id}';

                                                       document.getElementById('employeeForm').submit();">
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
                    </c:choose><!--/row-->
                <div class="well">
                    Look, I'm in a well!
                </div>
                <ol class="breadcrumb">
                    <li><a data-medium-element="true" data-placeholder="" class="editable" href="#" contenteditable="true">Home</a></li>
                    <li><a data-medium-element="true" data-placeholder="" class="editable" href="#" contenteditable="true">Library</a></li>
                    <li class="active">Data</li>
                </ol>
            </div>
        </div>
