package frontend.forms;

public class FormAttribute {
	
	public FormAttribute() { }
	
	private String name, value;
	private int type;
	private long subjectAttributeTypeFk;
	private long subjectTypeFk;
	private long orderby;
	private boolean required;
	
	public String getName() {
		return name;
	}
	
	public void setName(String attrName) {
		name = attrName;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String attrValue) {
		value = attrValue;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int attrType) {
		type = attrType;
	}
	
	public long getSubjectAttributeTypeFk() {
		return subjectAttributeTypeFk;
	}

	public void setSubjectAttributeTypeFk(long subjectAttributeTypeFk) {
		this.subjectAttributeTypeFk = subjectAttributeTypeFk;
	}

	public long getSubjectTypeFk() {
		return subjectTypeFk;
	}

	public void setSubjectTypeFk(long subjectTypeFk) {
		this.subjectTypeFk = subjectTypeFk;
	}

	public long getOrderby() {
		return orderby;
	}

	public void setOrderby(long orderby) {
		this.orderby = orderby;
	}
	
	public boolean getRequired() {
		return required;
	}
	
	public void setRequired(boolean r) {
		required = r;
	}

}
