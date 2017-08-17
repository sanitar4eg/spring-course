<html>
<head>
	<title>Login</title>
</head>
<body>
<div>
	<form name="f" action="/login" method="post">
		<fieldset>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<legend>Please Login</legend>

			<label for="username">Username</label>
			<input id="username" name="username"/>
			<label for="password">Password</label>
			<input type="password" id="password" name="password"/>
		<label for="remember-me">Remember Me: </label>
		<input type="checkbox" id="remember-me" name="remember-me"/>
			<div class="form-actions">
				<button class="btn">Log in</button>
			</div>
		</fieldset>
	</form>
</div>
</body>
</html>