<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<jsp:useBean id="subjectTypeFk" scope="request" class="java.lang.String" />
<script type="text/javascript" src="static/js/subject.js" ></script>

<input type="hidden" name="subjectTypeFk" value="<%=subjectTypeFk%>" />
<%
	int subjectType = Integer.parseInt(subjectTypeFk);
	switch (subjectType) {
	case 1:
		%><jsp:include page="person_form.jsp" /><%
		break;
	case 2:
		%><jsp:include page="enterprise_form.jsp" /><%
		break;
	case 3:
		%><jsp:include page="employee_form.jsp" /><%
		break;
	}
%>
</body>
</html>