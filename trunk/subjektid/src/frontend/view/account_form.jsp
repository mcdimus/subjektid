<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="backend.model.UserAccount"%>
<%@page import="java.util.ArrayList" %>
<%@page import="frontend.forms.AccountForm" %>
<jsp:useBean id="contacts" scope="request" class="java.util.ArrayList" />

		<tr>
			<th colspan="2" class="main">Account</th>
		</tr>
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
				DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
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