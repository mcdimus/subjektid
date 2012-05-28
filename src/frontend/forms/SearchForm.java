package frontend.forms;

import java.util.HashMap;

public class SearchForm {

	private HashMap<String, String> strings, numbers, dates;
	
	public SearchForm() { }
	
	public HashMap<String, String> getMap(int id) {
		switch (id) {
		case 1:
			return strings;
		case 2:
			return numbers;
		case 3:
			return dates;
		default:
			return null;
		}
	}
	
	public void setMap(HashMap<String, String> map, int id) {
		switch (id) {
		case 1:
			strings = map;
			break;
		case 2:
			numbers = map;
			break;
		case 3:
			dates = map;
			break;
		default:
			break;
		}
	}
}
