$(function() {
	
	var $select = $('select#subject_type'),
		$place = $('tfoot');
	$select.on('change', function() {
		if ($select.val() != 0) {
			$.post('ajax', { mode : "get", attribute : "subjectTypeFk",
				value : $select.val() }, function(answer) {
					$place.html('<tr><td colspan="2" class="centered">'
							+ '---- Subject attributes ----</td></tr>');
		        	$(answer).each(function(index, element) {
		        		var secondInput;
		        		if (element.type != 1) {
		        			secondInput = '<input type="text" name="'
			        				+ 'attribute_sval" />';
		        		} else {
		        			secondInput = '<input type="hidden" name="'
		        				+ 'attribute_sval" value="" />';
		        		}
		        		$place.append($('<tr><input type="hidden" name="'
		        				+ 'attribute_id" value="' + element.attr_id
		        				+ '" /><input type="hidden" name="'
		        				+ 'attribute_name" value="'
		        				+ element.name + '" />'
		        				+ '<input type="hidden" name="'
		        				+ 'attribute_type" value="'
		        				+ element.type + '" /><th>' + element.name
		        				+ '</th><td class="short">'
		        				+ '<input type="text" name="attribute_fval'
		        				+ '" />' + secondInput + '</td></tr>'));
			        });
		        	$place.append($('<tr><td><button type="submit" name="submitBtn">' +
						'Search</button></td><td></td></tr>'));
			}, 'json');
		} else {
			$place.html('<tr><td><button type="submit" name="submitBtn">' +
					'Search</button></td><td></td></tr>');
		}
	});

	$(document).on('click', 'a[name="deleteSubject"]', function(event) {
		event.preventDefault();
		if (confirm("Are you sure?")) {
			var data = {
					mode : "delete", 
					what : "subject", 
					subjectId : $(this).data("subjectId"), 
					subjectType : $(this).data("subjectType")
					};
			$.post('ajax', data, function(answer) {
				alert(answer);
			}, 'json').fail(function(answer) {
				alert("Something went wrong. Sorry!");
			});
		}
	});
	
});