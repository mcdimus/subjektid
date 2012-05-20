<%@page import="backend.model.EmployeeRoleType"%>
<%@page import="backend.model.Enterprise"%>
<%@ page import="frontend.forms.FormAttribute" %>
<%@ page import="frontend.forms.AddressForm" %>
<jsp:useBean id="enterpriseList" scope="request"
	class="java.util.ArrayList" />
<jsp:useBean id="employeeRoleTypeList" scope="request"
	class="java.util.ArrayList" />
<jsp:useBean id="employeeForm" scope="request" class="frontend.forms.EmployeeForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String firstName = "", lastName = "", identityCode = "", birthDate = "",
		country = "", county = "", townVillage = "", streetAddress = "",
		zipcode = "", status = "";
	if (employeeForm.getFirstName() != null) {
		firstName = employeeForm.getFirstName();
		lastName = employeeForm.getLastName();
		identityCode = employeeForm.getIdentityCode();
		birthDate = employeeForm.getBirthDate();
		
		AddressForm addressForm = employeeForm.getAddressForm();
		if (addressForm != null) {
			country = addressForm.getCountry();
			county = addressForm.getCounty();
			townVillage = addressForm.getTownVillage();
			streetAddress = addressForm.getStreetAddress();
			zipcode = addressForm.getZipcode();
		}
	}
%>

<form method="post"
	action="?mode=adding_new_subject&action=add_new_employee">
	<table>
		<tr>
			<th colspan="2" class="main"> New employee</th>
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
						for (int i = 0; i < enterpriseList.size(); i++) {
							enterprise = (Enterprise) enterpriseList.get(i);
					%>
					<option value="<%=enterprise.getEnterprise()%>">
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
			<td><select name="employee_role_type">
					<option value="">Select one...</option>
					<%
						EmployeeRoleType employeeRoleType;
						for (int i = 0; i < employeeRoleTypeList.size(); i++) {
							employeeRoleType = (EmployeeRoleType) 
									employeeRoleTypeList.get(i);
					%>
					<option value="<%=employeeRoleType.getEmployeeRoleType()%>">
						<%=employeeRoleType.getTypeName()%></option>
					<%
						}
					%>
			</select></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("employee_role_type") ? 
					errors.get("employee_role_type") : ""%></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="address_type_fk"
				value="1" />---------- Main address ---------</td>
		</tr>
		
		<tr>
			<th>Country</th>
			<td><input type="text" name="country" 
				value="<%=country%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("country") ? 
					errors.get("country") : ""%></td>
		</tr>
		
		<tr>
			<th>County</th>
			<td><input type="text" name="county" 
				value="<%=county%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("county") ? 
					errors.get("county") : ""%></td>
		</tr>
		
		<tr>
			<th>Town/village</th>
			<td><input type="text" name="town_village" 
				value="<%=townVillage%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("town_village") ? 
					errors.get("town_village") : ""%></td>
		</tr>
		
		<tr>
			<th>Street address</th>
			<td><input type="text" name="street_address" 
				value="<%=streetAddress%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("street_address") ? 
					errors.get("street_address") : ""%></td>
		</tr>
		
		<tr>
			<th>ZipCode</th>
			<td><input type="text" name="zipcode" 
				value="<%=zipcode%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("zipcode") ? 
					errors.get("zipcode") : ""%></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered">
				------- Person attributes ------</td>
		</tr>
		<%
			FormAttribute[] attributes = employeeForm.getAttributes();
			for (FormAttribute attribute : attributes) {
				String value = attribute.getValue() != null 
						? attribute.getValue() : "";
		%>
		<tr>
			<th><%=attribute.getName()%></th>
			<td><input type="text" name="<%=attribute.getName()%>"
				value="<%=value%>" /></td>
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
						? attribute.getValue() : "";
		%>
		<tr>
			<th><%=attribute.getName()%></th>
			<td><input type="text" name="<%=attribute.getName()%>"
				value="<%=value%>" /></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey(attribute.getName()) ? 
					errors.get(attribute.getName()) : ""%></td>
		</tr>
		<%
			}
		%>
		
		<tr>
			<td colspan="2"><button type="submit" name="submitBtn">Submit</button></td>
		</tr>
	</table>
</form>