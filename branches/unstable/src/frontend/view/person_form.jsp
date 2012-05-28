<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="frontend.forms.AddressForm" %>
<%@ page import="frontend.forms.FormAttribute" %>
<jsp:useBean id="personForm" scope="request" class="frontend.forms.PersonForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String firstName = "", lastName = "", identityCode = "", birthDate = "",
		status = "", action = "New person", button = "Submit", subjectId = "";
	ArrayList<AddressForm> addresses = new ArrayList<AddressForm>();
	
	if (personForm.getFirstName() != null) {
		firstName = personForm.getFirstName();
		lastName = personForm.getLastName();
		identityCode = personForm.getIdentityCode();
		birthDate = personForm.getBirthDate();
		if (personForm.getSubjectId() != null) {
			subjectId = personForm.getSubjectId();
			action = "Edit person";
			button = "Save";
		}
		
		if (personForm.getAddressForms() != null) {
			addresses = personForm.getAddressForms();
		}
	}
%>

<form method="post" action="?mode=subject&action=add_person">
	<input type="hidden" name="subjectId" value="<%=subjectId%>" />
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
				<input type="hidden" name="address_type_fk"value="1" />
				<input type="hidden" name="addressId" value="<%=addressId%>" />
				---------- Main address ---------
			</td>
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
			FormAttribute[] attributes = personForm.getAttributes();
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
			<td colspan="2"><button type="submit" name="submitBtn"><%=button%></button></td>
		</tr>
	</table>
</form>