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
			<h1>
				Welcome!
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<p>We have these available auditoriums:</p>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<table class="table table-bordered table-hover table-sm">
				<thead class="thead-default">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Seats Number</th>
					<th>Vip Seats</th>
				</tr>
				</thead>
				<!-- <#list auditoriums as auditorium> -->
			<tr>
				<td>${auditorium.id}
				<td><a href="/view/auditorium/${auditorium.name}">${auditorium.name}</a></td>
				<td>${auditorium.seatsNumber}</td>
				<td>${auditorium.vipSeats}</td>
				<!-- </#list> -->
			</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>