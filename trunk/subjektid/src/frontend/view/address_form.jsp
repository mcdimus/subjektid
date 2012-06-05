<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="frontend.forms.AddressForm" %>
<jsp:useBean id="addressForm" scope="request" class="frontend.forms.AddressForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />
<%

	String country = "", county = "", townVillage = "", streetAddress = "",
			zipcode = "", addressId = "";
	
	if (addressForm.getCountry() != null) {
		country = addressForm.getCountry();
		county = addressForm.getCounty();
		townVillage = addressForm.getTownVillage();
		streetAddress = addressForm.getStreetAddress();
		zipcode = addressForm.getZipcode();
		if (addressForm.getAddressId() != null) {
			addressId = addressForm.getAddressId();
		}
	}
%>
		<input type="hidden" name="addressId" value="<%=addressId%>" />
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
		
		<%
			ArrayList<AddressForm> addresses = new ArrayList<AddressForm>();
			if (addressForm.getAddresses() != null) {
				addresses = addressForm.getAddresses();
			}
		
			for (int i = 0; i < addresses.size(); i++) {
				AddressForm form = addresses.get(i);
		%>
		<tr>
			<td colspan="2" class="centered">
				<input type="hidden" name="address_type_fk" value="2" />
				<input type="hidden" name="addressId"
					value="<%=form.getAddressId() != null ? form.getAddressId() : ""%>" />
				---------- Additional address #<%=i + 1%> ---------
			</td>
		</tr>
		<tr>
			<th>Country</th>
			<td><input type="text" name="country" 
				value="<%=form.getCountry() != null ? form.getCountry() : ""%>"/></td>
		</tr>
		<tr>
			<th>County</th>
			<td><input type="text" name="county" 
				value="<%=form.getCounty() != null ? form.getCounty() : ""%>"/></td>
		</tr>
		<tr>
			<th>Town/village</th>
			<td><input type="text" name="town_village" 
				value="<%=form.getTownVillage() != null ? form.getTownVillage() : ""%>"/></td>
		</tr>
		<tr>
			<th>Street address</th>
			<td><input type="text" name="street_address" 
				value="<%=form.getStreetAddress() != null ? form.getStreetAddress() : ""%>"/></td>
		</tr>
		<tr>
			<th>ZipCode</th>
			<td><input type="text" name="zipcode" 
				value="<%=form.getZipcode() != null ? form.getZipcode() : ""%>"/></td>
		</tr>
		<%
			}
			
			if (addressId.length() != 0) {
		%>
		<tr>
			<td colspan="2" class="right">
				<input type="hidden" name="addr_counter" value=<%=addresses.size() - 1%> /> 
				<a href="#" class="additional_address">Add an address</a></td>
		</tr>
		<%
			}
		%>