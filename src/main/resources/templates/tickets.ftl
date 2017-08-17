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
			<p>
				<a class="btn btn-primary"
				   href="/view/booking?event=${event.name}&auditorium=${event.auditorium.name}&date=${event.dateTime}">Get
					as PDF</a>
				<button class="btn btn-primary" onclick="history.back()">Go Back</button>
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<p>We have these tickets for "${event.name}" in <a
				href="/view/auditorium/${event.auditorium.name}">${event.auditorium.name}</a>:</p>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<table class="table table-bordered table-hover table-sm">
				<thead class="thead-default">
				<tr>
					<th>ID</th>
					<th>Time</th>
					<th>Seats</th>
					<th>User email</th>
					<th>Price</th>
				</tr>
				</thead>
						<#list tickets as ticket>
			<tr>
				<td>${ticket.id}
				<td>${ticket.dateTime}</td>
				<td>${ticket.seats}</td>
				<td>${ticket.user.email}</td>
				<td>${ticket.price}</td>
						</#list>
			</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>