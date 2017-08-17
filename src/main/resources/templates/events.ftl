<html>
<head>
	<title>Events</title>
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
			<p>We have these available events:</p>
		</div>
	</div>
	<div class="row">
		<div class="col">
			<table class="table table-bordered table-hover table-sm">
				<thead class="thead-default">
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Rate</th>
					<th>Price</th>
					<th>Time</th>
					<th>Auditorium</th>
					<th>Tickets</th>
				</tr>
				</thead>
						<#list events as event>
			<tr>
				<td>${event.id}
				<td>${event.name}</td>
				<td>${event.rate}</td>
				<td>${event.basePrice}</td>
				<td>${event.dateTime}</td>
				<td>
					<a href="/view/auditorium/${event.auditorium.name}">${event.auditorium.name}</a>
				</td>
				<td>
					<a href="/view/booking?event=${event.name}&auditorium=${event.auditorium.name}&date=${event.dateTime}">View</a>
				</td>
						</#list>
			</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>