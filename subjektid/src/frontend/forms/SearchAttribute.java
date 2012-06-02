package frontend.forms;

public class SearchAttribute {

	private String name, type, firstValue, secondValue;

	public SearchAttribute() { }
	
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String t) {
		type = t;
	}

	public String getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(String fvalue) {
		firstValue = fvalue;
	}

	public String getSecondValue() {
		return secondValue;
	}

	public void setSecondValue(String svalue) {
		secondValue = svalue;
	}

	public boolean moreThanOneValue() {
		if (secondValue != null) {
			return true;
		} else {
			return false;
		}
	}

}