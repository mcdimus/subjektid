package frontend.forms;

import java.text.SimpleDateFormat;

import backend.model.UserAccount;

public class AccountForm {

	private String accountId;
	private String subjectTypeFk;
	private String subjectFk;
	private String username;
	private String password;
	private String status;
	private String validFrom;
	private String validTo;
	private String createdBy;
	private String created;
	private String passwordNeverExpires;

	public AccountForm() {
		
	}

	public AccountForm(UserAccount account) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		this.accountId = String.valueOf(account.getUserAccount());
		this.subjectTypeFk = String.valueOf(account.getSubjectTypeFk());
		this.subjectFk = String.valueOf(account.getSubjectFk());
		this.username = account.getUsername();
		this.password = account.getPassw();
		this.status = String.valueOf(account.getStatus());
		this.validFrom = account.getValidFrom() != null ?
				sdf.format(account.getValidFrom()) : null;
		this.validTo = account.getValidFrom() != null ?
				sdf.format(account.getValidTo()) : null;
		this.createdBy = String.valueOf(account.getCreatedBy());
		this.created = String.valueOf(account.getCreated());
		this.passwordNeverExpires = account.getValidFrom() != null ?
				account.getPasswordNeverExpires() : null;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the subjectTypeFk
	 */
	public String getSubjectTypeFk() {
		return subjectTypeFk;
	}

	/**
	 * @param subjectTypeFk
	 *            the subjectTypeFk to set
	 */
	public void setSubjectTypeFk(String subjectTypeFk) {
		this.subjectTypeFk = subjectTypeFk;
	}

	/**
	 * @return the subjectFk
	 */
	public String getSubjectFk() {
		return subjectFk;
	}

	/**
	 * @param subjectFk
	 *            the subjectFk to set
	 */
	public void setSubjectFk(String subjectFk) {
		this.subjectFk = subjectFk;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the validFrom
	 */
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom
	 *            the validFrom to set
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return the validTo
	 */
	public String getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo
	 *            the validTo to set
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the passwordNeverExpires
	 */
	public String getPasswordNeverExpires() {
		return passwordNeverExpires;
	}

	/**
	 * @param passwordNeverExpires
	 *            the passwordNeverExpires to set
	 */
	public void setPasswordNeverExpires(String passwordNeverExpires) {
		this.passwordNeverExpires = passwordNeverExpires;
	}

}
