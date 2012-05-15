<%@page import="backend.model.EmployeeRoleType"%>
<%@page import="backend.model.Enterprise"%>
<jsp:useBean id="enterpriseList" scope="request"
	class="java.util.ArrayList" />
<jsp:useBean id="employeeRoleTypeList" scope="request"
	class="java.util.ArrayList" />
<p>New employee</p>
<form method="post" action="add_new_employee">
	<table>
		<tr>
			<th>First name</th>
			<td><input type="text" name="first_name" /></td>
		</tr>
		<tr>
			<th>Last name</th>
			<td><input type="text" name="last_name" /></td>
		</tr>
		<tr>
			<th>Identity code</th>
			<td><input type="text" name="identity_code" /></td>
		</tr>
		<tr>
			<th>Date of birth</th>
			<td><input type="text" name="birthdate" /></td>
		</tr>
		<tr>
			<th>Enterprise</th>
			<td><select name="enterprise">
					<option value="-1">Select one...</option>
					<%
						Enterprise enterprise;
						for (int i = 0; i < enterpriseList.size(); i++) {
							enterprise = (Enterprise) enterpriseList.get(i);
					%>
					<option value="<%=enterprise.getEnterprise()%>"><%=enterprise.getName()%></option>
					<%
						}
					%>
			</select></td>
		</tr>
		<tr>
			<th>Role</th>
			<td><select name="employee_role">
					<option value="-1">Select one...</option>
					<%
						EmployeeRoleType employeeRoleType;
						for (int i = 0; i < employeeRoleTypeList.size(); i++) {
							employeeRoleType = (EmployeeRoleType) employeeRoleTypeList
									.get(i);
					%>
					<option value="<%=employeeRoleType.getEmployeeRoleType()%>"><%=employeeRoleType.getTypeName()%></option>
					<%
						}
					%>
			</select></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit" name="submitBtn">Submit</button></td>
		</tr>
	</table>
</form>