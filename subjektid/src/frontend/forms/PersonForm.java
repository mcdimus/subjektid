package frontend.forms;


public class PersonForm extends HumanForm {

	String EntPerRelId;
	String EntPerRelType;

	public PersonForm() { }

	public String getEntPerRelId() {
		return EntPerRelId;
	}

	public void setEntPerRelId(String entPerRelId) {
		EntPerRelId = entPerRelId;
	}

	public String getEntPerRelType() {
		return EntPerRelType;
	}

	public void setEntPerRelType(String entPerRelType) {
		EntPerRelType = entPerRelType;
	}

}
