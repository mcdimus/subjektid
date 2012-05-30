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
	
	$('a.additional_address').on('click', function() {
		var $addrNumInput = $('input[name="addr_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">' +
'---------- Additional address #' + (num + 1) + ' ---------</td></tr>' +
'<tr><th>Country</th><td><input type="text" name="country_' + num + '" /></td></tr>' +
'<tr><th>County</th><td><input type="text" name="county_' + num + '" /></td></tr>' +
'<tr><th>Town/village</th><td><input type="text" name="town_village_' + num + '" /></td></tr>' +
'<tr><th>Street address</th><td><input type="text" name="street_address_' + num + '" /></td></tr>' +
'<tr><th>ZipCode</th><td><input type="text" name="zipcode_' + num + '" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
});