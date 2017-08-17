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
			<h1>Auditorium: ${auditorium.name}</h1>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<ul>
				<li>Seats Number: ${auditorium.seatsNumber}</li>
				<li>Vip Seats: ${auditorium.vipSeats}</li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<button class="btn btn-primary" onclick="history.back()">Go Back</button>
		</div>
	</div>
</div>
</body>
</html>