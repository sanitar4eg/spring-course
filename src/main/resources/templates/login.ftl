<html>

<head>
	<title>Login</title>
	<link rel="stylesheet" href="/css/bootstrap-reboot.min.css">
	<link rel="stylesheet" href="/css/bootstrap.min.css">
</head>

<body>
<div class="container">
	<div class="row mt-4 mb-2">
		<div class="col">
			<h4>Please Login</h4>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<form name="f" action="/login" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" class="form-control" id="username" name="username"
						   placeholder="Enter your user name">
				</div>
				<div class="form-group">
					<label for="password">Password</label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				</div>
				<div class="form-check">
					<label class="form-check-label">
						<input type="checkbox" class="form-check-input" id="remember-me" name="remember-me"> Remember Me
					</label>
				</div>
				<button type="submit" class="btn btn-primary mt-2">Log in</button>
			</form>
		</div>
	</div>
</div>
</body>

</html>
