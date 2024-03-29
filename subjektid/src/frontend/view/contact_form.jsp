<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="frontend.forms.ContactForm"%>
<jsp:useBean id="contacts" scope="request" class="java.util.ArrayList" />

<tr>
	<th colspan="2" class="main">Contacts</th>
</tr>
<%
	for (int i = 0; i < contacts.size(); i++) {
		ContactForm contact = (ContactForm) contacts.get(i);
%>
<tr>
	<td colspan="2" class="centered"><input type="hidden"
		name="contact_id"
		value="<%=contact.getContactId() != null ? contact
						.getContactId() : ""%>" />
		--------------- #<%=i + 1%> --------------
		<input type="hidden" name="contact_orderby"
			value="<%=contact.getOrderBy() != null ? contact
					.getOrderBy() : ""%>"/></td>
</tr>
<tr>
	<th>Type</th>
	<td>
		<%
			String selected = "selected=\"selected\"";
		%> <select name="contact_type">
			<option <%=(contact.getContactType().equals("1")) ? selected
						: ""%>
				value="1">Email</option>
			<option <%=(contact.getContactType().equals("2")) ? selected
						: ""%>
				value="2">Phone number</option>
	</select>
	</td>
</tr>
<tr>
	<th>Contact</th>
	<td><input type="text" name="contact"
		value="<%=contact.getContact() != null ? contact.getContact()
						: ""%>" /></td>
</tr>
<tr>
	<th>Note</th>
	<td><input type="text" name="note"
		value="<%=contact.getNote() != null ? contact.getNote() : ""%>" /></td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td><a href="#" name="deleteContact"
		data-contact-id="<%=contact.getContactId() != null ? contact
						.getContactId() : ""%>">Delete</a>
	</td>
</tr>
<%
	}
%>

<tr>
	<td colspan="2" class="right"><input type="hidden"
		name="cont_counter" value=<%=contacts.size() - 1%> /> <a href="#"
		class="additional_contact">Add a contact</a></td>
</tr>