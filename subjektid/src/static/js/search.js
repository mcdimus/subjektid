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
		        		if (element.type != "1") {
			        		$place.append($('<tr><input type="hidden" name="' 
			        				+ element.name + '" value="'
			        				+ element.type + '" /><th>' + element.name
			        				+ '</th><td class="short">'
			        				+ '<input type="text" name="' + element.name
			        				+ '" /><input type="text" name="'
			        				+ element.name + '" /></td></tr>'));
		        		} else {
			        		$place.append($('<tr><input type="hidden" name="' 
			        				+ element.name + '" value="'
			        				+ element.type + '" /><th>' + element.name
			        				+ '</th><td>' + '<input type="text" name="'
			        				+ element.name + '" /></td></tr>'));
		        		}
			        });
		        	$place.append($('<tr><td><button type="submit" name="submitBtn">' +
						'Search</button></td></tr>'));
			}, 'json');
		} else {
			$place.html('<tr><td><button type="submit" name="submitBtn">' +
					'Search</button></td></tr>');
		}
	});

});