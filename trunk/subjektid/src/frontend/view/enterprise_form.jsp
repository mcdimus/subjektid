<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="frontend.forms.FormAttribute" %>
<jsp:useBean id="enterpriseForm" scope="request" class="frontend.forms.EnterpriseForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String name = "", fullName = "", country = "", county = "",
		townVillage = "", streetAddress = "", zipcode = "", status = "",
		action = "New enterprise", button = "Submit", subjectId = "";
	if (enterpriseForm.getName() != null) {
		name = enterpriseForm.getName();
		fullName = enterpriseForm.getFullName();
		if (enterpriseForm.getSubjectId() != null) {
			subjectId = enterpriseForm.getSubjectId();
			if (!subjectId.isEmpty()) {
				action = "Edit enterprise";
				button = "Save";
			}
		}
	}
%>

<form method="post" action="?mode=subject&action=add_enterprise">
	<input type="hidden" name="subject_id" value="<%=subjectId%>" />
	<div class="float-left">
	<table>
		<tr>
			<th colspan="2" class="main"><%=action%></th>
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
			<td colspan="2" class="centered">
				--------------------------------</td>
		</tr>
		<%
			if (enterpriseForm.getCustomerId() != null) {
		%>
		<tr>
			<th>Customer ?</th>
			<td class="centered">Yes
				<input type="hidden" name="customer_id"
					value="<%=enterpriseForm.getCustomerId()%>" />
			</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<th>Customer ?</th>
			<td class="centered"><input type="checkbox" name="customer"
				value="<%=enterpriseForm.getCustomer() != null 
				? "checked='checked'" : ""%>" /></td>
		</tr>
		<%
			}
		%>
		
		<tr>
			<td colspan="2" class="centered">
				------- Enterprise attributes ------</td>
		</tr>
		<%
			FormAttribute[] attributes = enterpriseForm.getAttributes();
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
			if (enterpriseForm.getCustomerId() != null
					&& !enterpriseForm.getCustomerId().isEmpty()) {
		%>
		<tr>
			<td colspan="2" class="centered">
				------ Customer attributes -----</td>
		</tr>
		<%
				attributes = enterpriseForm.getCustromerAttributes();
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
				<input type="hidden" name="address_type_fk" value="3" />
				---------- Legal address ---------
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
</form>