<%@page import="backend.model.EmployeeRoleType"%>
<%@page import="backend.model.Enterprise"%>
<%@ page import="frontend.forms.FormAttribute" %>
<%@ page import="frontend.forms.EmployeeRoleForm" %>
<%@ page import="java.util.ArrayList" %>
<jsp:useBean id="enterpriseList" scope="request"
	class="java.util.ArrayList" />
<jsp:useBean id="employeeRoleTypeList" scope="request"
	class="java.util.ArrayList" />
<jsp:useBean id="employeeForm" scope="request" class="frontend.forms.EmployeeForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String firstName = "", lastName = "", identityCode = "", birthDate = "",
		country = "", county = "", townVillage = "", streetAddress = "",
		zipcode = "", status = "", action = "New employee", button = "Submit",
		subjectId = "", employeeId = "";
	if (employeeForm.getFirstName() != null) {
		firstName = employeeForm.getFirstName();
		lastName = employeeForm.getLastName();
		identityCode = employeeForm.getIdentityCode();
		birthDate = employeeForm.getBirthDate();
		if (employeeForm.getSubjectId() != null) {
			subjectId = employeeForm.getSubjectId();
			employeeId = employeeForm.getEmployeeId();
			action = "Edit employee";
			button = "Save";
		}
	}
%>

<form method="post" action="?mode=subject&action=add_employee">
	<input type="hidden" name="subject_id" value="<%=subjectId%>" />
	<input type="hidden" name="employee_id" value="<%=employeeId%>" />
	<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main"><%=action%></th>
		</tr>
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="subject_type_fk"
				value="1" />---------- Person data ---------</td>
		</tr>
		
		<tr>
			<th>First name</th>
			<td><input type="text" name="first_name" 
				value="<%=firstName%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("first_name") ? 
					errors.get("first_name") : ""%></td>
		</tr>
		
		<tr>
			<th>Last name</th>
			<td><input type="text" name="last_name" 
				value="<%=lastName%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("last_name") ? 
					errors.get("last_name") : ""%></td>
		</tr>
		
		<tr>
			<th>Identity code</th>
			<td><input type="text" name="identity_code" 
				value="<%=identityCode%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("identity_code") ? 
					errors.get("identity_code") : ""%></td>
		</tr>
		
		<tr>
			<th>Date of birth</th>
			<td><input type="text" name="birthdate" 
				value="<%=birthDate%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("birthdate") ? 
					errors.get("birthdate") : ""%></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
				--------------------------------</td>
		</tr>
		<tr>
			<th>Enterprise</th>
			<td><select name="enterprise">
					<option value="">Select one...</option>
					<%
						Enterprise enterprise;
						String selected = "";
						String entName = "";
						for (int i = 0; i < enterpriseList.size(); i++) {
							selected = "";
							enterprise = (Enterprise) enterpriseList.get(i);
							if (enterprise.getEnterprise() == Long.parseLong(
									employeeForm.getEnterprise())) {
								entName = enterprise.getName(); // what for?
								selected = "selected=\"selected\"";
							}
					%>
					<option <%=selected %> value="<%=enterprise.getEnterprise()%>">
						<%=enterprise.getName()%></option>
					<%
						}
					%>
			</select></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("enterprise") ? 
					errors.get("enterprise") : ""%></td>
		</tr>
		
		<tr>
			<th>Role</th>
			<td>
				<script type="text/javascript">
				$(document).ready(function() {
					
					$('select[name="employee_role_type"]').on('change', function() {
						$this = $(this);
						var $newInput = $('<input type="hidden">').attr({
							name : "role_type",
							value : $this.val()
							});
						
						$this.after("<br />", $this.find("option:selected").text(), $newInput);
					});
				});
				
				</script>
				<select name="employee_role_type">
						<option value="">Select one...</option>
						<%
							EmployeeRoleType employeeRoleType;
							ArrayList<EmployeeRoleForm> roles =
									employeeForm.getRoles();
							for (int i = 0; i < employeeRoleTypeList.size(); i++) {
								employeeRoleType = (EmployeeRoleType) 
										employeeRoleTypeList.get(i);
						%>
						<option value="<%=employeeRoleType.getEmployeeRoleType()%>">
							<%=employeeRoleType.getTypeName()%></option>
						<%
							}
						%>
				</select>
				<%
				ArrayList<EmployeeRoleForm> emplyeeRoles = employeeForm.getRoles();
				for (EmployeeRoleForm role : emplyeeRoles) {
					out.println("<br/>" + role.getRoleName());
				}
				%>
			</td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("employee_role_type") ? 
					errors.get("employee_role_type") : ""%></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
				------- Person attributes ------</td>
		</tr>
		<%
			FormAttribute[] attributes = employeeForm.getAttributes();
			for (FormAttribute attribute : attributes) {
				String value = attribute.getValue() != null 
						? attribute.getValue() : "",
					formAttributeId = attribute.getFormAttributeId() != null
						? attribute.getFormAttributeId() : "";
		%>
		<tr>
			<th><%=attribute.getName()%></th>
			<td><input type="text" name="<%=attribute.getName()%>"
				value="<%=value%>" />
				<input type="hidden" name="attribute_id" 
					value="<%=formAttributeId%>" />
			</td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey(attribute.getName()) ? 
					errors.get(attribute.getName()) : ""%></td>
		</tr>
		<%
			}
		%>
		
		<tr>
			<td colspan="2" class="centered">
				------- Employee attributes ------</td>
		</tr>
		<%
			attributes = employeeForm.getEmployeeAttributes();
			for (FormAttribute attribute : attributes) {
				String value = attribute.getValue() != null 
						? attribute.getValue() : "",
					formAttributeId = attribute.getFormAttributeId() != null
						? attribute.getFormAttributeId() : "";
		%>
		<tr>
			<th><%=attribute.getName()%></th>
			<td><input type="text" name="<%=attribute.getName()%>"
				value="<%=value%>" />
				<input type="hidden" name="attribute_id" 
					value="<%=formAttributeId%>" />	
			</td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey(attribute.getName()) ? 
					errors.get(attribute.getName()) : ""%></td>
		</tr>
		<%
			}
		%>
		
		<tr>
			<td colspan="2"><button type="submit" name="submit_button"><%=button%></button></td>
		</tr>
	</table>
</div>
<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main">Addresses</th>
		</tr>
		<tr>
			<td colspan="2" class="centered">
				<input type="hidden" name="address_type_fk" value="1" />
				---------- Main address ---------
			</td>
		</tr>
		
		<jsp:include page="address_form.jsp" />
		
		
	</table>
</div>
<div class="float-left">
	<table>
		<%
			if (!subjectId.isEmpty()) {
		%>
		
		<jsp:include page="contact_form.jsp" />
		
		<%
			}
		%>
	</table>
</div>
<div class="float-left">
	<table>
		<%
			if (!subjectId.isEmpty()) {
		%>
		
		<jsp:include page="account_form.jsp" />
		
		<%
			}
		%>
	</table>
</div>
</form>