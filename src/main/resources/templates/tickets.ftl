<html>
<head>
	<title>Welcome!</title>
</head>
<body>
<h1>
	Welcome!
</h1>

<p>We have these tickets for "${event.name}" in <a
	href="/view/auditorium/${event.auditorium.name}">${event.auditorium.name}</a>:
<table border=1>
	<thead>
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
	<td>${ticket.dateTime}
	<td>${ticket.seats}
	<td>${ticket.user.email}
<td>${ticket.price}
</#list>
</table>

<#--TODO: Add header with "Accept=application/pdf" to request-->
<a href="/view/booking?event=${event.name}&auditorium=${event.auditorium.name}&date=${event.dateTime}">Get as PDF</a>
<p>

<a href="javascript:history.back()">Go Back</a>
</body>
</html>