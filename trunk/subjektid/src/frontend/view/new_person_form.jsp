<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="frontend.forms.AddressForm" %>
<jsp:useBean id="personForm" scope="request" class="frontend.forms.PersonForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />
<% AddressForm addressForm = personForm.getAddressForm(); %>

<p>New person</p>
<form method="post"
	action="?mode=adding_new_subject&action=add_new_person">
	<table>
		<tr>
			<td colspan="2"><input type="hidden" name="subject_type_fk"
				value="1" />----------Person data---------</td>
		</tr>
		
		<tr>
			<th>First name</th>
			<td><input type="text" name="first_name" 
				value="<%=personForm.getFirstName()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("first_name")%></td>
		</tr>
		
		<tr>
			<th>Last name</th>
			<td><input type="text" name="last_name" 
				value="<%=personForm.getLastName()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("last_name")%></td>
		</tr>
		
		<tr>
			<th>Identity code</th>
			<td><input type="text" name="identity_code" 
				value="<%=personForm.getIdentityCode()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("indentity_code")%></td>
		</tr>
		
		<tr>
			<th>Date of birth</th>
			<td><input type="text" name="birthdate" 
				value="<%=personForm.getBirthDate()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("birthdate")%></td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="hidden" name="address_type_fk"
				value="1" />----------Main address---------</td>
		</tr>
		
		<tr>
			<th>Country</th>
			<td><input type="text" name="country" 
				value="<%=addressForm.getCountry()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("country")%></td>
		</tr>
		
		<tr>
			<th>County</th>
			<td><input type="text" name="county" 
				value="<%=addressForm.getCounty()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("county")%></td>
		</tr>
		
		<tr>
			<th>Town/village</th>
			<td><input type="text" name="town_village" 
				value="<%=addressForm.getTownVillage()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("town_village")%></td>
		</tr>
		
		<tr>
			<th>Street address</th>
			<td><input type="text" name="street_address" 
				value="<%=addressForm.getStreetAddress()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("street_address")%></td>
		</tr>
		
		<tr>
			<th>ZipCode</th>
			<td><input type="text" name="zipcode" 
				value="<%=addressForm.getZipcode()%>"/></td>
		</tr>
		<tr>
			<td class="error" colspan="2"><%=errors.get("zipcode")%></td>
		</tr>
		
		<tr>
			<td colspan="2"><input type="hidden" name="address_type_fk"
				value="1" />-------Person attributes (optional)------</td>
		</tr>
		<tr>
			<th>Eesti resident</th>
			<td><input type="text" name="eesti_resident" /></td>
		</tr>
		<tr>
			<th>Laste arv</th>
			<td><input type="text" name="laste_arv" /></td>
		</tr>
		<tr>
			<th>Gender</th>
			<td><select name="gender">
					<option></option>
					<option>Male</option>
					<option>Female</option>
			</select></td>
		</tr>
		<tr>
			<th>Nationality</th>
			<td><input type="text" name="nationality" /></td>
		</tr>
		<tr>
			<th>Religion</th>
			<td><input type="text" name="religion" /></td>
		</tr>
		<tr>
			<th>Driving license</th>
			<td><input type="text" name="driving_license" /></td>
		</tr>
		<tr>
			<th>Favorite number</th>
			<td><input type="text" name="fav_number" /></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit" name="submitBtn">Submit</button></td>
		</tr>
	</table>
</form>