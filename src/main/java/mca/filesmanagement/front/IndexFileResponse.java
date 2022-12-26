package mca.filesmanagement.front;

import java.util.Date;

public class IndexFileResponse {
	
	private String id;
	private Date procesDate;
	private String phaseCode;
	private String phaseName;
	private String initOption;
	private String description;
	private String procesCode;
	
	public IndexFileResponse() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the procesDate
	 */
	public Date getProcesDate() {
		return procesDate;
	}

	/**
	 * @param procesDate the procesDate to set
	 */
	public void setProcesDate(Date procesDate) {
		this.procesDate = procesDate;
	}

	/**
	 * @return the phaseCode
	 */
	public String getPhaseCode() {
		return phaseCode;
	}

	/**
	 * @param phaseCode the phaseCode to set
	 */
	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	/**
	 * @return the phaseName
	 */
	public String getPhaseName() {
		return phaseName;
	}

	/**
	 * @param phaseName the phaseName to set
	 */
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	/**
	 * @return the initOption
	 */
	public String getInitOption() {
		return initOption;
	}

	/**
	 * @param initOption the initOption to set
	 */
	public void setInitOption(String initOption) {
		this.initOption = initOption;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the procesCode
	 */
	public String getProcesCode() {
		return procesCode;
	}

	/**
	 * @param procesCode the procesCode to set
	 */
	public void setProcesCode(String procesCode) {
		this.procesCode = procesCode;
	}
}
