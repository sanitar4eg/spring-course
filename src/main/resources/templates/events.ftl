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
</#list>
</table>


</body>
</html>