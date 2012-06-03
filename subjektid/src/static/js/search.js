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
			        				+ 'attribute' + index + '" value="'
			        				+ element.attr_id + '" /><th>' + element.name
			        				+ '</th><td class="short">'
			        				+ '<input type="text" name="attribute_' + index +
			        				+ '" /><input type="text" name="'
			        				+ 'attribute_' + index + '" /></td></tr>'));
		        		} else {
			        		$place.append($('<tr><input type="hidden" name="' 
			        				+ 'attribute' + index + '" value="'
			        				+ element.attr_id + '" /><th>' + element.name
			        				+ '</th><td><input type="text" name="'
			        				+ 'attribute_' + index + '" /></td></tr>'));
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