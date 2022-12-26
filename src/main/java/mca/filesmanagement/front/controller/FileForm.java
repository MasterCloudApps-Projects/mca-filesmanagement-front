package mca.filesmanagement.front.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mca.filesmanagement.apigateway.messaging.FileInfo;
import mca.filesmanagement.front.InitialOptionResponse;
import mca.filesmanagement.front.PhaseResponse;

public class FileForm {

	private FileInfo file;
	private String phaseSelected;
	private List<PhaseResponse> availablesPhases = new ArrayList<>(0);
	private List<InitialOptionResponse> initialOptions = new ArrayList<>(0);
	
	public FileForm() {
		super();
	}

	/**
	 * @return the file
	 */
	public FileInfo getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FileInfo file) {
		this.file = file;
	}
	
	public String getPhaseCode() {
		return Optional.ofNullable(this.file).get().getPhaseCode();
	}
	
	public String getCode() {
		return Optional.ofNullable(this.file).get().getCode();
	}
	
	public String getProcesCode() {
		return Optional.ofNullable(this.file).get().getProcesCode();
	}

	/**
	 * @return the phaseSelected
	 */
	public String getPhaseSelected() {
		return phaseSelected;
	}

	/**
	 * @param phaseSelected the phaseSelected to set
	 */
	public void setPhaseSelected(String phaseSelected) {
		this.phaseSelected = phaseSelected;
	}

	/**
	 * @return the availablesPhases
	 */
	public List<PhaseResponse> getAvailablesPhases() {
		return availablesPhases;
	}

	/**
	 * @param availablesPhases the availablesPhases to set
	 */
	public void setAvailablesPhases(List<PhaseResponse> availablesPhases) {
		this.availablesPhases = availablesPhases;
	}

	public void setPhaseCode(String phaseSelected) {
		this.file.setPhaseCode(phaseSelected);
	}

	/**
	 * @return the initialOptions
	 */
	public List<InitialOptionResponse> getInitialOptions() {
		return initialOptions;
	}

	/**
	 * @param initialOptions the initialOptions to set
	 */
	public void setInitialOptions(List<InitialOptionResponse> initialOptions) {
		this.initialOptions = initialOptions;
	}
}
