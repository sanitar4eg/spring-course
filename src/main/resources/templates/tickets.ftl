<html>
<head>
	<title>Welcome!</title>
	<style>
		body {
			color: #444444;
		}

		a {
			font-style: italic;
			color: #43c543;
		}

		a:visited {
			color: #002700;
		}

		.table {
			border: 1px solid #736f6f;
			margin: 20px;
			min-width: 900px;
		}

		.table-content {
			display: flex;
			border-top: 1px solid #736f6f;
		}

		.table-header {
			background-color: #69ce96;
			display: flex;
			font-weight: 700;
		}

		.table-cell {
			flex: 1 0 0;
			text-align: center;
			padding: 10px;
			border-right: 1px solid #736f6f;
		}

		.table-cell:last-child {
			border: 0;
		}
	</style>
</head>
<body>
<h1>Welcome!</h1>

<p>
	<a href="/view/booking?event=${event.name}&auditorium=${event.auditorium.name}&date=${event.dateTime}">Get as
		PDF</a>
	<a href="javascript:history.back()">Go Back</a>
</p>

<p>
	We have these tickets for "${event.name}" in <a
	href="/view/auditorium/${event.auditorium.name}">${event.auditorium.name}</a>:
</p>
<div class="table">
	<div class="table-header">
		<div class="table-cell">ID</div>
		<div class="table-cell">Time</div>
		<div class="table-cell">Seats</div>
		<div class="table-cell">User email</div>
		<div class="table-cell">Price</div>
	</div>
<#list tickets as ticket>
	<div class="table-content">
		<div class="table-cell">${ticket.id}</div>
		<div class="table-cell">${ticket.dateTime}</div>
		<div class="table-cell">${ticket.seats}</div>
		<div class="table-cell">${ticket.user.email}</div>
		<div class="table-cell">${ticket.price}</div>
	</div>
</#list>
	<div/>
</body>
</html>