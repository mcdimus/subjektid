<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="frontend.forms.FormAttribute" %>
<%@ page import="backend.model.Enterprise"%>
<%@ page import="backend.model.EntPerRelationType"%>
<jsp:useBean id="enterpriseList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="ent_per_rels" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="personForm" scope="request" class="frontend.forms.PersonForm" />
<jsp:useBean id="errors" scope="request" class="java.util.HashMap" />

<%
	String firstName = "", lastName = "", identityCode = "", birthDate = "",
		country = "", county = "", townVillage = "", streetAddress = "",
		zipcode = "", status = "", action = "New person", button = "Submit",
		subjectId = "";
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
	}
%>

<form method="post" action="?mode=subject&action=add_person">
<div class="float-left">
	<input type="hidden" name="subject_id" value="<%=subjectId%>" />
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
				----------------------------------</td>
		</tr>
		<%
			if (personForm.getCustomerId() != null) {
		%>
		<tr>
			<th>Customer ?</th>
			<td class="centered">Yes
				<input type="hidden" name="customer_id"
					value="<%=personForm.getCustomerId()%>" />
			</td>
		</tr>
		<%
			} else {
		%>
		<tr>
			<th>Customer ?</th>
			<td class="centered"><input type="checkbox" name="customer"
				<%=personForm.getCustomer() != null ? "checked='checked'" : ""%> /></td>
		</tr>
		
		<%
			}
		%>
		
		<tr>
			<td colspan="2" class="centered">
				------- Person attributes ------</td>
		</tr>
		<%
			FormAttribute[] attributes = personForm.getAttributes();
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
			if (!personForm.getCustomerId().isEmpty()) {
		%>
		<tr>
			<td colspan="2" class="centered">
				------ Customer attributes -----</td>
		</tr>
		<%
				attributes = personForm.getCustromerAttributes();
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
			<th colspan="2" class="main">Enterprise relation
				<input type="hidden" value="<%=personForm.getEntPerRelId()
					!= null ? personForm.getEntPerRelId() : ""%>"/></th>
		</tr>
		<tr>
			<th>Enterprise</th>
			<td><select name="enterprise">
					<option value=""></option>
					<%
						Enterprise enterprise;
						for (int i = 0; i < enterpriseList.size(); i++) {
							enterprise = (Enterprise) enterpriseList.get(i);
					%>
					<option value="<%=enterprise.getEnterprise()%>"
						<%=personForm.getEnterprise() != null &&
							personForm.getEnterprise().equals(
							String.valueOf(enterprise.getEnterprise())) ? 
							"selected='selected'" : "" %>>
						<%=enterprise.getName()%>
					</option>
					<%
						}
					%>
			</select></td>
		</tr>
		<tr>
			<th>Relation type</th>
			<td><select name="relation_type">
					<option value=""></option>
					<%
						EntPerRelationType rel;
						for (int i = 0; i < ent_per_rels.size(); i++) {
							rel = (EntPerRelationType) ent_per_rels.get(i);
					%>
					<option value="<%=rel.getTypeName()%>" 
						<%=personForm.getEntPerRelType() != null &&
							personForm.getEntPerRelType().equals(
							String.valueOf(rel.getEntPerRelationType())) ? 
							"selected='selected'" : "" %>>
						<%=rel.getTypeName()%>
					</option>
					<%
						}
					%>
			</select></td>
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


			