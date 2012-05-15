<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Add new subject</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="static/js/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="static/css/style.css" />

<script type="text/javascript">
	$(document).ready(function() {
		$("select[name=subject_type]").on('change', function() {

			$(this).parent().find("div.form").css("display", "none");
			if ($(this).val() == 1) {
				alert($(this).val());
				$("#person_form").css("display", "block");
			} else if ($(this).val() == 2) {
				alert($(this).val());
				$("#enterprise_form").css("display", "block");
			} else if ($(this).val() == 3) {
				alert($(this).val());
				$("#employee_form").css("display", "block");
			} 
		});
	});
</script>

</head>
<body>
	<jsp:include page="header.jsp" />
	Subject type:
	<select name="subject_type">
		<option value="1">Person</option>
		<option value="2">Enterprise</option>
		<option value="3">Employee</option>
	</select>

	<div id="person_form" class="form" style="display: block">
		<jsp:include page="new_person_form.jsp" />
	</div>
	<div id="enterprise_form" class="form" style="display: none">
		<jsp:include page="new_enterprise_form.jsp" />
	</div>
	<div id="employee_form" class="form" style="display: none">
		<jsp:include page="new_employee_form.jsp" />
	</div>
</body>
</html>