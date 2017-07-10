<html>
<head>
	<title>Welcome!</title>
</head>
<body>
<h1>
	Welcome!
</h1>

<p>We have these available events:
<table border=1>
	<thead>
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
	<td>${event.name}
	<td>${event.rate}
	<td>${event.basePrice}
	<td>${event.dateTime}
<td><a href="/view/auditorium/${event.auditorium.name}">${event.auditorium.name}</a>
<td><a href="/view/booking?event=${event.name}&auditorium=${event.auditorium.name}&date=${event.dateTime}">View</a>
</#list>
</table>


</body>
</html>