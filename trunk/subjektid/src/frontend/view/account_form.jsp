<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="backend.model.UserAccount"%>
<%@page import="java.util.ArrayList"%>
<%@page import="frontend.forms.AccountForm"%>
<jsp:useBean id="employeeForm" scope="request"
	class="frontend.forms.EmployeeForm" />
<jsp:useBean id="accounts" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	AccountForm accountForm = employeeForm.getAccForm();
%>
<tr>
	<th colspan="2" class="main">Account
		<input type="hidden" name="account_id" value="<%=accountForm != null ?
				accountForm.getAccountId() : ""%>" />
	</th>
</tr>
<tr>
	<th>Username</th>
	<td><input type="text" name="username"
		value="<%=accountForm.getUsername() != null ? accountForm.getUsername() : ""%>" />
	</td>
</tr>
<tr>
	<td class="error" colspan="2"><%=errors.containsKey("username") ?
			errors.get("username") : ""%></td>
</tr>
<tr>
	<th>Password</th>
	<td><input type="password" name="password" /></td>
</tr>
<tr>
	<td class="error" colspan="2"><%=errors.containsKey("password") ?
			errors.get("password") : ""%></td>
</tr>

<tr>
	<th>Valid from</th>
	<%
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String formattedDate = sdf.format(new Date());
	%>
	<td><input type="text" name="valid_From"
		value="<%=accountForm.getValidFrom() != null ? accountForm.getValidFrom()
					: formattedDate%>" />
	</td>
<tr>
	<td class="error" colspan="2"><%=errors.containsKey("valid_from") ?
			errors.get("valid_from") : ""%></td>
</tr>
<tr>
	<th>Valid to</th>
	<%
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(formattedDate));
		c.add(Calendar.YEAR, 1); // number of years to add
		formattedDate = sdf.format(c.getTime()); // the new date
	%>
	<td><input type="text" name="valid_to"
		value="<%=accountForm.getValidTo() != null ? accountForm.getValidTo()
					: formattedDate%>" />
	</td>
<tr>
	<td class="error" colspan="2"><%=errors.containsKey("valid_to") ?
			errors.get("valid_to") : ""%></td>
</tr>
	<%
		String selectedY = "", selectedN = "";
		if (accountForm.getPasswordNeverExpires() != null) {
			if (accountForm.getPasswordNeverExpires().equals("Y")) {
				selectedY = "selected";
			} else {
				selectedN = "selected";
			}
		}
	%>
<tr>
	<th>Pass Never Exp.</th>
	<td>
		<select name="password_never_expires">
			<option value="Y" <%=selectedY%>>Yes</option>
			<option value="N" <%=selectedN%>>No</option>
		</select>
	</td>
</tr>
<tr>
	<th>&nbsp;<input type="hidden" name="status"
		value="<%=accountForm.getStatus() != null ? accountForm.getStatus() : "1"%>" />
		<input type="hidden" name="created_by"
		value="<%=accountForm.getCreatedBy() != null ? accountForm.getCreatedBy()
					: session.getAttribute("employee_id")%>" />
		<input type="hidden" name="created"
		value="<%=accountForm.getCreated() != null ? accountForm.getCreated()
					: new Date().getTime()%>" />
	</th>
	<td>
	<% if (accountForm != null && accountForm.getUsername() != "") { %>
		<a href="#" name="deleteAccount" data-account-id="<%=accountForm.getAccountId()%>">Delete</a>
	<%} %>
	</td>

</tr>