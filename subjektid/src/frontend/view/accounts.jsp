<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="backend.model.UserAccount"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="accounts" scope="request" class="java.util.ArrayList" />
<jsp:include page="header.jsp" />

<h1>Accounts:</h1>
<br />
<%
	if (accounts.size() > 0) {
%>
<table class="results">
	<tr>
		<th>#</th>
		<th>Username</th>
		<th>Password</th>
		<th>Status</th>
		<th>Valid from</th>
		<th>Valid to</th>
		<th>Created by</th>
		<th>Created</th>
		<th>Pass Never Exp.</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		for (int i = 0; i < accounts.size(); i++) {
				UserAccount account = (UserAccount) accounts.get(i);
	%>
	<tr>
		<td><%=i + 1%></td>
		<td><%=account.getUsername()%></td>
		<td><%=account.getPassw()%></td>
		<td><%=account.getStatus()%></td>
		<td><%=account.getValidFrom()%></td>
		<td><%=account.getValidTo()%></td>
		<td><%=account.getCreatedBy()%></td>
		<td><%=account.getCreated()%></td>
		<td><%=account.getPasswordNeverExpires()%></td>
		<td><a
			href="?mode=account&action=edit&id=<%=account.getUserAccount()%>">Edit</a></td>
		<td><a
			href="?mode=account&action=delete&id=<%=account.getUserAccount()%>">Delete</a></td>
	</tr>
	<%
		}
	%>
</table>
<%
	} else {
%>
<p>No accounts information found.</p>
<%
	}
%>
<br />

<form method="post" action="?mode=account&action=form">
	<button type="submit">Add new account</button>
</form>
</body>
</html>