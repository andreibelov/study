<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><c:choose>
    <c:when test="${requestScope.logindata == 'login'}">
        <c:set var="hideLogin" value=""/>
        <c:set var="hideSignup" value="style='display: none;'"/>
    </c:when>
    <c:when test="${requestScope.logindata == 'signup'}">
        <c:set var="hideLogin" value="style='display: none;'"/>
        <c:set var="hideSignup" value=""/>
    </c:when>
</c:choose>
    <div class="center-block" style="max-width: 400px;min-width: 400px; padding-top: 50px">
        <div class="well">
            <div class="row panel-heading">
                <div id="login-form-link" class="col-xs-6 active">
                    <h3 class="form-signin form-signin-heading">Login</h3>
                </div>
                <div id="register-form-link" class="col-xs-6">
                    <h3 class="form-signin form-signin-heading">Sign up</h3>
                </div>
            </div>
            <c:if test="${not empty requestScope.errormessage}">
                <div class="alert alert-danger alert-dismissible" role="alert"
                     style="margin-top: 20px; margin-bottom: 0;">
                    <span class="close" data-dismiss="alert">&times;</span>
                    <strong>Warning! </strong>${requestScope.errormessage}
                </div>
            </c:if>
            <div class="row">
                <div id="login-form"  class="col-xs-12" ${hideLogin}>
                    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/login">
                        <input type="hidden" name="action" value="login">
                        <label for="inputEmail" class="sr-only">Email address</label>
                        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                        <label for="inputPassword" class="sr-only">Password</label>
                        <input type="password" name="password" autocomplete="off" id="inputPassword" class="form-control" placeholder="Password" required>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" value="remember-me"> Remember me
                            </label>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                    </form>
                    <div class="text-center">
                    <a href="${pageContext.request.contextPath}/login?action=recover"
                       tabindex="5" class="text-muted">Forgot Password?</a>
                </div>
                </div>
                <div id="register-form" class="col-xs-12" ${hideSignup}>
                    <form class="form-horizontal" action="${pageContext.request.contextPath}/login"
                          method="POST" role="form" data-toggle="validator">
                        <input type="hidden" name="action" value="signup">
                        <div class="form-group">
                            <label class="control-label col-xs-4" for="username">Username</label>
                            <div class="col-xs-8">
                                <input id="username" name="username" placeholder="" class="form-control" type="text">
                                <p class="help-block">Username can contain any letters or numbers, without spaces</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4" for="email">E-mail</label>
                            <div class="col-xs-8">
                                <input id="email" name="email" placeholder="" class="form-control" type="email">
                                <p class="help-block">Please provide your E-mail</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4" for="password">Password</label>
                            <div class="col-xs-8">
                                <input id="password" name="password" placeholder="" class="form-control" type="password">
                                <p class="help-block">Password should be at least 6 characters</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4" for="password_confirm">Password (Confirm)</label>
                            <div class="col-xs-8">
                                <input id="password_confirm" name="password_confirm" placeholder="" class="form-control" type="password">
                                <p class="help-block">Please confirm password</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-4" for="terms"></label>
                            <div class="col-xs-8">
                                <input id="terms" name="terms" type="checkbox"> I agree with the <a href="#">Terms and Conditions</a>.
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <!-- Button -->
                            <div class="col-xs-12">
                                <button class="btn btn-lg btn-success btn-block">Sign up</button>
                                <p class="help-block"></p>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
