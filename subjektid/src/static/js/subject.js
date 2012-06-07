$(document).ready(function() {
	
	$('a.additional_address').on('click', function() {
		var $addrNumInput = $('input[name="addr_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">'
+ '<input type="hidden" name="address_type_fk" value="2" />'
+ '<input type="hidden" name="address_id" />'
+ '---------- Additional address #' + (num + 1) + ' ---------</td></tr>'
+ '<tr><th>Country</th><td><input type="text" name="country" /></td></tr>'
+ '<tr><th>County</th><td><input type="text" name="county" /></td></tr>'
+ '<tr><th>Town/village</th><td><input type="text" name="town_village" /></td></tr>'
+ '<tr><th>Street address</th><td><input type="text" name="street_address" /></td></tr>'
+ '<tr><th>ZipCode</th><td><input type="text" name="zipcode" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
	$('a.additional_contact').on('click', function() {
		var $addrNumInput = $('input[name="cont_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">'
+ '---------------  #' + (num + 1) + ' --------------<input type="hidden"'
+ ' name="contact_id" /></td></tr>'
+ '<tr><th>Type</th><td><select name="contact_type"><option value="1">Email</option>'
+ '<option value="2">Phone number</option></select></td></tr><tr><th>Contact</th>'
+ '<td><input type="text" name="contact" /></td></tr><tr><th>Note</th>'
+ '<td><input type="text" name="note" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
	$('select[name="employee_role_type"]').on('change', function() {
		$this = $(this);
		var $newInput = $('<input type="hidden" name="role_type_id" value="'
				+ $this.val() + '"/><input type="hidden" name="role_id" />');
		
	$this.after("<br />", $this.find("option:selected").text(), $newInput);
	
	});
	
	$(document).on('click', 'a[name="deleteContact"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Are you sure?")) {
			var data = {
					mode : "delete", 
					what : "contact", 
					contactId : $(this).data("contactId")
					};
			$.post('ajax', data, function(answer) {
				var message = '';
				if (answer.answer == 'OK') {
					//alert("Contact successfully deleted.");
					$parentTr = $this.parents('tr');
					// delete parent, and four preceding trs
					for (var i = 0; i < 4; i++ ){
						$parentTr.prev().remove();
					}
					$parentTr.remove();
				} else {
					alert("Contact is not deleted. Sorry.");
				}
				
				//$('div#message').html(message);
				
			}, 'json').fail(function(answer) {
				alert("Something went wrong. Sorry!");
			});
			//console.log(data);
		}
	});
	
	$(document).on('click', 'a[name="deleteAccount"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Are you sure?")) {
			var data = {
					mode : "delete", 
					what : "account", 
					accountId : $(this).data("accountId")
					};
			$.post('ajax', data, function(answer) {
				var message = '';
				if (answer.answer == 'OK') {
					$this.parent().prev().append("Deleted");
					$this.parents('table').find('input').val("");
					$this.remove();
				} else {
					alert("Account is not deleted. Sorry.");
				}
				
				//$('div#message').html(message);
				
			}, 'json').fail(function(answer) {
				alert("Something went wrong. Sorry!");
			});
			//console.log(data);
		}
	});
});