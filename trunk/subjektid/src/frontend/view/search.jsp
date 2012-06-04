<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<%@ page import="frontend.forms.SearchResult" %>
<jsp:useBean id="action" scope="request" class="java.lang.String" />
<jsp:useBean id="results" scope="request" class="java.util.ArrayList" />
<script type="text/javascript" src="static/js/search.js" ></script>
	<form method="post" action="?mode=search&action=search">
		<div class="search">
		<table>
			<tr>
				<th>Subject type</th>
				<td>
					<select name="subject_type" id="subject_type">
						<option value="0">All subjects</option>
						<option value="1">Person</option>
						<option value="2">Enterprise</option>
						<option value="3">Employee</option>
						<option value="4">Client</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>First name</th>
				<td><input type="text" name="fname" /></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><input type="text" name="lname" /></td>
			</tr>
			
			<tr><td colspan="2" class="centered">
					---------- Address ---------</td></tr>
			
			<jsp:include page="address_form.jsp" />
			
			<tfoot>
				<tr>
					<td><button type="submit" name="submitBtn">Search</button></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		</div>
	</form>
	<div class="search results">
	<h3>Search results</h3>
	<table class="results">
		<%
			if (action.equals("search")) {
				if (results.size() != 0) {
		%><tr><td colspan="4">Found <%=results.size()%> persons.</td></tr>
		<tr><th>Result #</th><th>Subject name</th><th>Subject type</th><th>Edit</th></tr><%
					SearchResult res;
					for (int i = 0; i < results.size(); i++) {
						res = (SearchResult) results.get(i);
		%><tr><td><%=i + 1%></td><td><%=res.getSubjectName()%></td>
				<td><%=res.getSubjectType()%></td>
			<%
				String link = "?mode=subject&action=edit_subject&subject_id="
						+ res.getSubjectId() + "&subject_type=";
				if (res.getSubjectType().equals("person")) {
					link += "1";
				} else {
					link += "2";
				}
			%>
				<td><a href="<%=link%>" target="_blank">Edit</a></td></tr><%
					}
				} else {
		%><tr><td colspan="4">No persons found.</td></tr><%
				}
			}
		%>
	</table>
	</div>
</body>
</html>