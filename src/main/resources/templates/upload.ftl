<html>
<head>
	<title>Welcome!</title>
</head>
<body>
<h1>
	Welcome!
</h1>
<#--TODO: Success message-->
<#--<p>${message}</p>-->
<p> Please Select file: </p>

<div>
	<form method="POST" enctype="multipart/form-data" action="/upload/users">
		<table>
			<tr>
				<td>Users to upload:</td>
				<td><input type="file" name="file"/></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Upload"/></td>
			</tr>
		</table>
	</form>
</div>

<div>
	<form method="POST" enctype="multipart/form-data" action="/upload/events">
		<table>
			<tr>
				<td>Events to upload:</td>
				<td><input type="file" name="file"/></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Upload"/></td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>