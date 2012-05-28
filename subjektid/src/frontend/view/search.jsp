<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/new_subject.js" ></script>
	<form method="post" action="?mode=search&action=search">
	Subject type:
		<input type="text" name="search-keyword" />
		<select>
			<option value="0">All subjects</option>
			<option value="1">Person</option>
			<option value="2">Enterprise</option>
			<option value="3">Employee</option>
			<option value="4">Client</option>
		</select>
		<input type="submit" value="search" />
	</form>
</body>
</html>