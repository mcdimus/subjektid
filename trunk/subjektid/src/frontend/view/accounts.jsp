<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="backend.model.UserAccount"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="accounts" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="message" scope="request" class="java.lang.String" />
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
<%
	if (message != null && !message.isEmpty()) {
		out.println(message);
	}
%>
<br />
<h2>Add new account:</h2>
<form method="post" action="?mode=account&action=add">
	<table>
		<tr>
			<th>Username</th>
			<td><input type="text" name="username" /></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="password" /></td>
		</tr>

		<tr>
			<th>Valid from</th>
			<%
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = df.format(new Date());
			%>
			<td><input type="text" name="validFrom"
				value="<%=formattedDate%>" /></td>
		</tr>
		<tr>
			<th>Valid to</th>
			<%
				Calendar c = Calendar.getInstance();
				c.setTime(df.parse(formattedDate));
				c.add(Calendar.YEAR, 1); // number of years to add
				formattedDate = df.format(c.getTime()); // the new date
			%>
			<td><input type="text" name="validTo" value="<%=formattedDate%>" /></td>
		</tr>
		<tr>
			<th>Pass Never Exp.</th>
			<td><input type="text" name="passwordNeverExpires" value="1" /></td>
		</tr>
		<tr>
			<th>&nbsp;<input type="hidden" name="status" value="1" /> <input
				type="hidden" name="createdBy"
				value="<%=session.getAttribute("employee_id")%>" /> <input
				type="hidden" name="created" value="<%=new Date().getTime()%>" /></th>
			<td><button type="submit">Save</button></td>
		</tr>

	</table>

</form>
</body>
</html>