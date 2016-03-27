package org.yeastrc.proxl.xml.crux.objects;

import java.io.File;
import java.util.Map;

/**
 * Proxl-relevant params associated with a search-for-xlinks crux analysis.
 * 
 * @author Michael Riffle
 * @date Mar 27, 2016
 *
 */
public class CruxParams {

	
	public Map<String, Double> getStaticMods() {
		return staticMods;
	}
	public void setStaticMods(Map<String, Double> staticMods) {
		this.staticMods = staticMods;
	}
	public double getLinkerMass() {
		return linkerMass;
	}
	public void setLinkerMass(double linkerMass) {
		this.linkerMass = linkerMass;
	}
	public File getParamsFile() {
		return paramsFile;
	}
	public void setParamsFile(File paramsFile) {
		this.paramsFile = paramsFile;
	}
	public String getFastaFilename() {
		return fastaFilename;
	}
	public void setFastaFilename(String fastaFilename) {
		this.fastaFilename = fastaFilename;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCommandLineRun() {
		return commandLineRun;
	}
	public void setCommandLineRun(String commandLineRun) {
		this.commandLineRun = commandLineRun;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public File getAnalysisDirectory() {
		return analysisDirectory;
	}
	public void setAnalysisDirectory(File analysisDirectory) {
		this.analysisDirectory = analysisDirectory;
	}
	public byte[] getParamFileContents() {
		return paramFileContents;
	}
	public void setParamFileContents(byte[] paramFileContents) {
		this.paramFileContents = paramFileContents;
	}


	private Map<String, Double> staticMods;
	private double linkerMass;
	private File paramsFile;
	private String fastaFilename;
	private String version;
	private String commandLineRun;
	private String linkerName;
	private File analysisDirectory;
	private byte[] paramFileContents;
	
}
