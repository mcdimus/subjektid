$(document).ready(function() {
	val subjectTypeFk = $('input[name="subjectTypeFk"]').val();
	
	switch(subjectTypeFk) {
	case 1:
		$("#person_form").css("display", "block");
		break;
	case 2:
		$("#enterprise_form").css("display", "block");
		break;
	case 3:
		$("#employee_form").css("display", "block");
		break;
	case 4:
		// TODO: client form should be here
		break;
	}
});