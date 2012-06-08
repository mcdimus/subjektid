$(document).ready(function() {
	
	$('a.additional_address').on('click', function(event) {
		event.preventDefault();
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
	
	$('a.additional_contact').on('click', function(event) {
		event.preventDefault();
		var $addrNumInput = $('input[name="cont_counter"]');
		var num = parseInt($addrNumInput.val()) + 1;
		$(this).parents('tr').before($('<tr><td colspan="2" class="centered">'
+ '---------------  #' + (num + 1) + ' --------------<input type="hidden"'
+ ' name="contact_id" /><input type="hidden" name="contact_orderby" value="'
+ (num + 1) + '" /></td></tr>'
+ '<tr><th>Type</th><td><select name="contact_type"><option value="1">Email</option>'
+ '<option value="2">Phone number</option></select></td></tr><tr><th>Contact</th>'
+ '<td><input type="text" name="contact" /></td></tr><tr><th>Note</th>'
+ '<td><input type="text" name="note" /></td></tr>'));
		$addrNumInput.val(num);
	});
	
	$('select[name="employee_role_type"]').on('change', function() {
		$this = $(this);
		var text = $this.find("option:selected").text();
		if (text != "Select one...") {
			var $newInput = $('<input type="hidden" name="role_type_id" value="'
					+ $this.val() + '"/><input type="hidden" name="role_id" />'
					+ '<input type="hidden" name="role_name"'
					+ ' value="' + text + '" />"');
			
			$this.after("<br />", text, $newInput);
		}
	
	});
	
	$('button[name="replace_main_address"]').on('click', function() {
		var newId = $(this).siblings('input'),
			oldId = $($('input[name="address_id"]').get(0));
		$.post('ajax', { mode : "update", what : "address", old_id : oldId.val(),
			new_id : newId.val() }, function() {
				var newTR = newId.parents('tr'), oldTR =
					$($('input[name="address_type_fk"]').get(1)).parents('tr');
				var newInput, oldInput, spare;
				spare = newId.val();
				newId.val(oldId.val());
				oldId.val(spare);
				for (var i = 0; i < 5; i++) {
					newTR = newTR.prev();
					oldTR = oldTR.prev().prev();
					newInput = newTR.find('input');
					oldInput = oldTR.find('input');
					spare = newInput.val();
					newInput.val(oldInput.val());
					oldInput.val(spare);
				}
		});
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
				if (answer.answer == 'OK') {
					//alert("Contact successfully deleted.");
					$parentTr = $this.parents('tr');
					// delete parent, and four preceding trs
					for (var i = 0; i < 4; i++) {
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
	
	$(document).on('click', 'a[name="deleteRole"]', function(event) {
		event.preventDefault();
		$this = $(this);
		if (confirm("Are you sure?")) {
			var data = {
					mode : "delete", 
					what : "role", 
					roleId : $this.data("roleId")
					};
			$.post('ajax', data, function(answer) {
				if (answer.answer == 'OK') {
				
					$this.prevUntil('br').remove();
					$this.after($("<span></span>").text("deleted"));
					$this.remove();
				} else {
					alert("Role is not deleted. Sorry.\nMay be it does not exist yet?");
				}
				
				//$('div#message').html(message);
				
			}, 'json').fail(function(answer) {
				alert("Something went wrong. Sorry!");
			});
			//console.log(data);
		}
	});
});