<html>
<head>
	<title>Welcome!</title>
</head>
<body>
<h1>
	Welcome!
</h1>

<p>We have these available auditoriums:
<table border=1>
	<thead>
	<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Seats Number</th>
		<th>Vip Seats</th>
	</tr>
	</thead>
<#list auditoriums as auditorium>
<tr>
	<td>${auditorium.id}
	<td><a href="/view/auditorium/${auditorium.name}">${auditorium.name}</a>
	<td>${auditorium.seatsNumber}
<td>${auditorium.vipSeats}
</#list>
</table>


</body>
</html>