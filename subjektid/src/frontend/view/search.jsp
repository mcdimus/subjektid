<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/search.js" ></script>
	<form method="post" action="?mode=search">
		<table>
			<tr>
				<th>Subject type</th>
				<td>
					<select id="subject_type">
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
				<td><input type="text" name="subject_fname" /></td>
			</tr>
			<tr>
				<th>ame</th>
				<td><input type="text" name="subject_lname" /></td>
			</tr>
			
			<tr><td colspan="2" class="centered">
					---------- Address ---------</td></tr>
			
			<jsp:include page="address_form.jsp" />
			
			<tfoot>
			<tr>
				<td><button type="submit" name="submitBtn">Search</button></td>
			</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>