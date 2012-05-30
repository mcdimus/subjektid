$(function() {
	
	var $select = $('select#subject_type'),
		$place = $('tfoot');
	$select.on('change', function() {
		if ($select.val() != 0) {
			$.post('ajax', { attribute : "subjectTypeFk",
				value : $select.val() }, function(answer) {
					$place.html('<tr><td colspan="2" class="centered">'
							+ '---- Subject attributes ----</td></tr>');
		        	$(answer).each(function(index, element) {
		        		$place.append($('<tr><th>' + element + '</th><td>'
			            		+ '<input type="text" name="' + element
			            		+ '" /></td></tr>'));
			        });
		        	$place.append('<tr><td><button type="submit" name="submitBtn">'
        			+ 'Search</button></td></tr>');
			}, 'json');
		} else {
			$place.html('');
		}
	});

});