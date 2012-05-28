<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<jsp:useBean id="subjectTypeFk" scope="request" class="java.lang.String" />
<script type="text/javascript" src="static/js/edit_subject.js" ></script>

<input type="hidden" name="subjectTypeFk" value="<%=subjectTypeFk%>" />
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