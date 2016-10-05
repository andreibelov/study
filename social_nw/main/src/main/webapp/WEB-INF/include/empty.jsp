<div class="col-sm-12 col-md-6">
    <h2>Sign Up</h2>
    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/signup">
        <label>First Name</label>
        <input name="firstname" class="form-control" type="text">
        <label>Last Name</label>
        <input name="lastname" class="form-control" type="text">
        <label>Email Address</label>
        <input name="email" class="form-control" type="email">
        <label>Username</label>
        <input name="username" class="form-control" type="text">
        <label>Password</label>
        <input name="password" class="form-control" type="password">
        <label><input name="terms" type="checkbox"> I agree with the <a href="#">Terms and Conditions</a>.</label>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="Sign up" ></button>
        <div class="clearfix"></div>
    </form>
</div>