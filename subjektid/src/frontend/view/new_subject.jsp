<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<script type="text/javascript" src="static/js/new_subject.js" ></script>
<script type="text/javascript" src="static/js/subject.js" ></script>
<jsp:useBean id="subjectTypeFk" scope="request" class="java.lang.String" />

<input type="hidden" name="subjectTypeFk" 
	value="<%=subjectTypeFk != null ? subjectTypeFk : ""%>" />
Subject type:
<select name="subject_type">
	<option value="1">Person</option>
	<option value="2">Enterprise</option>
	<option value="3">Employee</option>
</select>

<div id="person_form" class="form" style="display: block">
	<jsp:include page="person_form.jsp" />
</div>
<div id="enterprise_form" class="form" style="display: none">
	<jsp:include page="enterprise_form.jsp" />
</div>
<div id="employee_form" class="form" style="display: none">
	<jsp:include page="employee_form.jsp" />
</div>
</body>
</html>