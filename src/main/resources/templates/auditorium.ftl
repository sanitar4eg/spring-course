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

		li {
			font-style: italic;
			margin: 10px;
		}
	</style>
</head>
<body>
<h1>Auditorium: ${auditorium.name}</h1>
<ul>
	<li>Seats Number: ${auditorium.seatsNumber}</li>
	<li>Vip Seats: ${auditorium.vipSeats}</li>
</ul>
<a href="javascript:history.back()">Go Back</a>
</body>
</html>