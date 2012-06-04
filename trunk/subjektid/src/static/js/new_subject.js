$(document).ready(function() {
	var indicatorLine = $('div.indicator-line div'),
			subjectTypeFk = $('input[name="subjectTypeFk"]').val();
	
	if (subjectTypeFk.length != 0) {
		var intSubjTypeFk = parseInt(subjectTypeFk);
		$("div.form").css("display", "none");
		switch(intSubjTypeFk) {
		case 1:
			$("#person_form").css("display", "block");
			break;
		case 2:
			$("#enterprise_form").css("display", "block");
			break;
		case 3:
			$("#employee_form").css("display", "block");
			break;
		default:
			break;
		}
	}
	
	$("select[name=subject_type]").on('change', function() {

		$(this).parent().find("div.form").css("display", "none");
		if ($(this).val() == 1) {
			$("#person_form").css("display", "block");
		} else if ($(this).val() == 2) {
			$("#enterprise_form").css("display", "block");
		} else if ($(this).val() == 3) {
			$("#employee_form").css("display", "block");
		}
		
		if (indicatorLine.hasClass('error')) {
			indicatorLine.removeClass('error').addClass('welcome').text('WELCOME!');
			$('td.error').css('display', 'none');
		}
		
	});
	
});