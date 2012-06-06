$(document).ready(function() {
	
	$('a.additional_address').on('click', function() {
		var $addrNumInput = $('input[name="addr_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">' +
'---------- Additional address #' + (num + 1) + ' ---------</td></tr>' +
'<tr><th>Country</th><td><input type="text" name="country" /></td></tr>' +
'<tr><th>County</th><td><input type="text" name="county" /></td></tr>' +
'<tr><th>Town/village</th><td><input type="text" name="town_village" /></td></tr>' +
'<tr><th>Street address</th><td><input type="text" name="street_address" /></td></tr>' +
'<tr><th>ZipCode</th><td><input type="text" name="zipcode" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
	$('a.additional_contact').on('click', function() {
		var $addrNumInput = $('input[name="cont_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">' +
'---------------  #' + (num + 1) + ' --------------</td></tr>'
+ '<tr><th>Type</th><td><select name="contact_type"><option value="1">Email</option>'
+ '<option value="2">Phone number</option></select></td></tr><tr><th>Contact</th>'
+ '<td><input type="text" name="contact" /></td></tr><tr><th>Note</th>'
+ '<td><input type="text" name="note" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
});