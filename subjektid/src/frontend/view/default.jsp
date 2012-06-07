<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<jsp:include page="header.jsp" />

	<form method="post" action="?mode=subject&action=add_new_subject">
		<button type="submit">Add new subject</button>
	</form>
	
	<form method="post" action="?mode=search">
		<button type="submit">Search</button>
	</form>
<!-- 	<form method="post" action="?mode=account&action=view"> -->
<!-- 		<button type="submit">Accounts</button> -->
<!-- 	</form> -->
</body>
</html>