<p>New person</p>
<form method="post"
	action="?mode=adding_new_subject&action=add_new_person">
	<table>
		<tr>
			<td colspan="2"><input type="hidden" name="subject_type_fk"
				value="1" />----------Person data---------</td>
		</tr>
		<tr>
			<th>First name</th>
			<td><input type="text" name="first_name" /></td>
		</tr>
		<tr>
			<th>Last name</th>
			<td><input type="text" name="last_name" /></td>
		</tr>
		<tr>
			<th>Identity code</th>
			<td><input type="text" name="identity_code" /></td>
		</tr>
		<tr>
			<th>Date of birth</th>
			<td><input type="text" name="birthdate" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="hidden" name="address_type_fk"
				value="1" />----------Main address---------</td>
		</tr>
		<tr>
			<th>Country</th>
			<td><input type="text" name="country" /></td>
		</tr>
		<tr>
			<th>County</th>
			<td><input type="text" name="county" /></td>
		</tr>
		<tr>
			<th>Town/village</th>
			<td><input type="text" name="town_village" /></td>
		</tr>
		<tr>
			<th>Street address</th>
			<td><input type="text" name="street_address" /></td>
		</tr>
		<tr>
			<th>Zip code</th>
			<td><input type="text" name="zipcode" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="hidden" name="address_type_fk"
				value="1" />-------Person attributes (optional)------</td>
		</tr>
		<tr>
			<th>Gender</th>
			<td><select name="gender">
					<option></option>
					<option>Male</option>
					<option>Female</option>
			</select></td>
		</tr>
		<tr>
			<th>Eesti resident</th>
			<td><input type="text" name="eesti_resident" /></td>
		</tr>
		<tr>
			<th>Nationality</th>
			<td><input type="text" name="nationality" /></td>
		</tr>
		<tr>
			<th>Religion</th>
			<td><input type="text" name="religion" /></td>
		</tr>
		<tr>
			<th>Favorite number</th>
			<td><input type="text" name="fav_number" /></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit" name="submitBtn">Submit</button></td>
		</tr>
	</table>
</form>