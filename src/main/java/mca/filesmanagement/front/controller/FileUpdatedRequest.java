package mca.filesmanagement.front.controller;

public class FileUpdatedRequest {
	
	private String code;
	private String description;
	private Long initialOption;
	
	public FileUpdatedRequest() {
		super();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the initialOption
	 */
	public Long getInitialOption() {
		return initialOption;
	}

	/**
	 * @param initialOption the initialOption to set
	 */
	public void setInitialOption(Long initialOption) {
		this.initialOption = initialOption;
	}
}
