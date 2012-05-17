<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="status" scope="request" class="java.lang.String" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
<title>r12_subjektid</title>
</head>
<body>
	<div class="indicator-line">
		<div class="<%=status.toLowerCase()%>"><%=status%>!</div>
	</div>
	<div>
		User <strong><%=session.getAttribute("username")%></strong> is logged in.
		<form id="logout-form" method="post" action="?mode=login&action=logout">
			<button type="submit">Logout</button>
		</form>
	</div>
	<ul>
		<li><a href="s">servlet</a></li>
		<li><a href="log.txt" target="_blank">log.txt</a></li>
	</ul>
	<hr />