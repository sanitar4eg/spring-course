<html>

<head>
	<title>Welcome!</title>
	<link rel="stylesheet" href="/css/bootstrap-reboot.min.css">
	<link rel="stylesheet" href="/css/bootstrap.min.css">
</head>

<body>
<div class="container">
	<div class="row">
		<div class="col">
			<h1>Welcome!</h1>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<p> Please Select file: </p>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<div class="card">
				<div class="card-header">
					Users to upload
				</div>
				<div class="p-3">
					<form method="POST" enctype="multipart/form-data" action="/upload/users">
						<div class="form-group">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input type="file" class="form-control-file" id="file" name="file">
						</div>
						<button type="submit" class="btn btn-primary">Upload</button>
					</form>
				</div>
			</div>
		</div>
		<div class="col">
			<div class="card">
				<div class="card-header">
					Events to upload
				</div>
				<div class="p-3">
					<form method="POST" enctype="multipart/form-data" action="/upload/events">
						<div class="form-group">

							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input type="file" class="form-control-file" id="file" name="file">
						</div>
						<button type="submit" class="btn btn-primary">Upload</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>

</html>
