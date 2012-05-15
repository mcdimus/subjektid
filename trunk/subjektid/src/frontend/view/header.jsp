<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
<title>r12_subjektid</title>
</head>
<body>
	User
	<strong><%=session.getAttribute("username")%></strong> logged in.
	<form method="post" action="?action=logout">
		<button type="submit">Logout</button>
	</form>
	<br />
	<ul>
		<li><a href="s">servlet</a></li>
		<li><a href="log.txt" target="_blank">Log.txt</a></li>
	</ul>
	<hr />