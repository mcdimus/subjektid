<%@page import="frontend.forms.ContactForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<%@ page import="frontend.forms.SearchResult"%>
<%@ page import="frontend.forms.ContactForm"%>
<jsp:useBean id="searchForm" scope="request" class="frontend.forms.SearchForm" />
<jsp:useBean id="action" scope="request" class="java.lang.String" />
<jsp:useBean id="results" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="htmlWithResults" scope="request"
	class="java.lang.String" />
<script type="text/javascript" src="static/js/search.js"></script>
<form method="post" action="?mode=search&action=search_xml">
	<div class="float-left">
		<table>
			<tr>
			<%
				int subjectType = (int) searchForm.getSubjectType();
				String valOne = "", valTwo = "", valThree = "", valFour = "";
				switch(subjectType) {
				case 1:
					valOne = "selected";
					break;
				case 2:
					valTwo = "selected";
					break;
				case 3:
					valThree = "selected";
					break;
				case 4:
					valFour = "selected";
					break;
				}
			%>
				<th>Subject type</th>
				<td>
					<select name="subject_type" id="subject_type">
							<option value="0">All subjects</option>
							<option value="1" <%=valOne%>>Person</option>
							<option value="2" <%=valTwo%>>Enterprise</option>
							<option value="3" <%=valThree%>>Employee</option>
							<option value="4" <%=valFour%>>Client</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>First name</th>
				<td><input type="text" name="fname"
					value="<%=searchForm.getFirstName() != null ?
							searchForm.getFirstName() : ""%>" /></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><input type="text" name="lname"
					value="<%=searchForm.getLastName() != null ?
							searchForm.getLastName() : ""%>" /></td>
			</tr>

			<tr>
				<td colspan="2" class="centered">---------- Address ---------</td>
			</tr>

			<jsp:include page="address_form.jsp" />

			<tr>
				<td colspan="2" class="centered">---------- Contact ---------</td>
			</tr>
			<%
				ContactForm form = searchForm.getContactForm();
				String contactType = "", contact = "", note = "";
				if (form != null) {
					contactType = form.getContactType();
					contact = form.getContact();
					note = form.getNote();
				}
			%>
			<tr>
				<th>Type</th>
				<td><select name="contact_type">
					<option value="1" <%=contactType.equals("1")
						? "selected" : ""%>>Email</option>
					<option value="2" <%=contactType.equals("2")
						? "selected" : ""%>>Phone number</option>
				</select></td>
			</tr>
			<tr>
				<th>Contact</th>
				<td><input type="text" name="contact" value="<%=contact%>" /></td>
			</tr>
			<tr>
				<th>Note</th>
				<td><input type="text" name="contact_note" value="<%=note%>" /></td>
			</tr>
			
			<tfoot>
				<tr>
					<td><button type="submit" name="submitBtn">Search</button></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
	</div>
</form>
<div class="float-left results">
	<h3>Search results</h3>

	<%
		if (action.equals("search")) {
	%>
	<table class="results">
		<%
			if (results.size() != 0) {
		%><tr>
			<td colspan="4">Found <%=results.size()%> persons.
			</td>
		</tr>
		<tr>
			<th>Result #</th>
			<th>Subject name</th>
			<th>Subject type</th>
			<th>Edit</th>
		</tr>
		<%
			SearchResult res;
					for (int i = 0; i < results.size(); i++) {
						res = (SearchResult) results.get(i);
		%><tr>
			<td><%=i + 1%></td>
			<td><%=res.getSubjectName()%></td>
			<td><%=res.getSubjectType()%></td>
			<%
// 				String link = "?mode=subject&action=edit_subject&subject_id="
// 									+ res.getSubjectId() + "&subject_type=";
// 							if (res.getSubjectType().equals("person")) {
// 								link += "1";
// 							} else {
// 								link += "2";
// 							}
			%>
			<td>
			<form method="post" action="?mode=subject&action=edit_subject" >
				<input type="hidden" name="subject_id" value="<%=res.getSubjectId() %>" />
				<input type="hidden" name="subject_type" value="<%=res.getSubjectType().equals("person") ? "1" : "2" %>" />
				<input type="submit" name="submitBtn" value="Edit" />
			</form>
<%-- 			<a href="<%=link%>">Edit</a> --%>
			
			</td>
		</tr>
		<%
			}
				} else {
		%><tr>
			<td colspan="4">No persons found.</td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>
	<div id="message"></div>
	<%
	if (htmlWithResults != null && !htmlWithResults.isEmpty()) {
		out.print(htmlWithResults);
	}
	%>

</div>
</body>
</html>