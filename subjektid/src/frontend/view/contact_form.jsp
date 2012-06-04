<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="frontend.forms.ContactForm" %>
<jsp:useBean id="contacts" scope="request" class="java.util.ArrayList" />
		<%
			if (true) {
				//contacts.size() != 0
		%>
			<td colspan="2" class="centered">
					------------- Contacts ------------</td>
		<%
			}
			for (int i = 0; i < contacts.size(); i++) {
				ContactForm contact = (ContactForm) contacts.get(i);
		%>
		<tr>
			<td colspan="2" class="centered">
		
				<input type="hidden" name="contactId"
					value="<%=contact.getContactId() != null ? contact.getContactId() : ""%>" />
				---------------  #<%=i + 1%> --------------
			</td>
		</tr>
		<tr>
			<th>Type</th>
			<td><select name="contact_type">
				<option value="1">Email</option>
				<option value="2">Phone number</option>
			</select></td>
		</tr>
		<tr>
			<th>Contact</th>
			<td><input type="text" name="county" 
				value="<%=contact.getContact() != null ? contact.getContact() : ""%>"/></td>
		</tr>
		<%
			}
		%>
		
		<tr>
			<td colspan="2" class="right">
				<input type="hidden" name="cont_counter" value=<%=contacts.size() - 1%> /> 
				<a href="#" class="additional_contact">Add a contact</a></td>
		</tr>