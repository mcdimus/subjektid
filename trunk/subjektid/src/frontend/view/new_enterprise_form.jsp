<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="frontend.forms.AddressForm" %>
<%@ page import="frontend.forms.FormAttribute" %>
<jsp:useBean id="enterpriseForm" scope="request" class="frontend.forms.EnterpriseForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String name = "", fullName = "", country = "", county = "",
		townVillage = "", streetAddress = "", zipcode = "", status = "";
	if (enterpriseForm.getName() != null) {
		name = enterpriseForm.getName();
		fullName = enterpriseForm.getFullName();
		
		AddressForm addressForm = enterpriseForm.getAddressForm();
		if (addressForm != null) {
			country = addressForm.getCountry();
			county = addressForm.getCounty();
			townVillage = addressForm.getTownVillage();
			streetAddress = addressForm.getStreetAddress();
			zipcode = addressForm.getZipcode();
		}
	}
%>

<form method="post" action="?mode=adding_new_subject&action=add_new_enterprise">
	<table>
		<tr>
			<th colspan="2" class="main"> New enterprise</th>
		</tr>
		<tr>
			<th>Name</th>
			<td><input type="text" name="name" value="<%=name%>" /></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("name") ? 
					errors.get("name") : ""%></td>
		</tr>
		<tr>
			<th>Full name</th>
			<td><input type="text" name="full_name" value="<%=fullName%>" /></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.containsKey("full_name") ? 
					errors.get("full_name") : ""%></td>
		</tr>
		
		<tr>
			<td colspan="2" class="centered"><input type="hidden" name="address_type_fk"
				value="2" />---------- Legal address ---------</td>
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
				------- Enterprise attributes ------</td>
		</tr>
		<%
			FormAttribute[] attributes = enterpriseForm.getAttributes();
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