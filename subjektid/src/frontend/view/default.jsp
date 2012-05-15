<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/css/style.css" />
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="indicator-line">
		<div class="WELCOME">WELCOME!</div>
	</div>
	<form method="post" action="?action=add_new_subject">
		<button type="submit">Add new subject</button>
	</form>
</body>
</html>