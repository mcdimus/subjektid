<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<jsp:include page="header.jsp" />
	<script type="text/javascript">
	$(document).ready(function() {
		$("select[name=subject_type]").on('change', function() {

			$(this).parent().find("div.form").css("display", "none");
			if ($(this).val() == 1) {
				$("#person_form").css("display", "block");
			} else if ($(this).val() == 2) {
				$("#enterprise_form").css("display", "block");
			} else if ($(this).val() == 3) {
				$("#employee_form").css("display", "block");
			} 
		});
	});
    </script>
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