package org.yeastrc.proxl.xml.crux.objects;

import java.util.Map;

public class CruxResult {

	
	public CruxReportedPeptide getReportedPeptide() {
		return reportedPeptide;
	}

	public void setReportedPeptide(CruxReportedPeptide reportedPeptide) {
		this.reportedPeptide = reportedPeptide;
	}

	public int getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(int scanNumber) {
		this.scanNumber = scanNumber;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public double getPrecursorMz() {
		return precursorMz;
	}

	public void setPrecursorMz(double precursorMz) {
		this.precursorMz = precursorMz;
	}

	public double getPrecursorNeutralMass() {
		return precursorNeutralMass;
	}

	public void setPrecursorNeutralMass(double precursorNeutralMass) {
		this.precursorNeutralMass = precursorNeutralMass;
	}

	public double getCalcMassMono() {
		return calcMassMono;
	}

	public void setCalcMassMono(double calcMassMono) {
		this.calcMassMono = calcMassMono;
	}

	public double getCalcMassAvg() {
		return calcMassAvg;
	}

	public void setCalcMassAvg(double calcMassAvg) {
		this.calcMassAvg = calcMassAvg;
	}

	public double getMassErrorPPM() {
		return massErrorPPM;
	}

	public void setMassErrorPPM(double massErrorPPM) {
		this.massErrorPPM = massErrorPPM;
	}

	public double getxCorrScore() {
		return xCorrScore;
	}

	public void setxCorrScore(double xCorrScore) {
		this.xCorrScore = xCorrScore;
	}

	public double getxCorrRank() {
		return xCorrRank;
	}

	public void setxCorrRank(double xCorrRank) {
		this.xCorrRank = xCorrRank;
	}

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}

	public double getIonCurrentTotal() {
		return ionCurrentTotal;
	}

	public void setIonCurrentTotal(double ionCurrentTotal) {
		this.ionCurrentTotal = ionCurrentTotal;
	}

	public double getqValueDecoy() {
		return qValueDecoy;
	}

	public void setqValueDecoy(double qValueDecoy) {
		this.qValueDecoy = qValueDecoy;
	}

	public double getqValueBH() {
		return qValueBH;
	}

	public void setqValueBH(double qValueBH) {
		this.qValueBH = qValueBH;
	}

	public double getFdrDecoy() {
		return fdrDecoy;
	}

	public void setFdrDecoy(double fdrDecoy) {
		this.fdrDecoy = fdrDecoy;
	}

	public double getFdrBH() {
		return fdrBH;
	}

	public void setFdrBH(double fdrBH) {
		this.fdrBH = fdrBH;
	}

	public double getpValueBonf() {
		return pValueBonf;
	}

	public void setpValueBonf(double pValueBonf) {
		this.pValueBonf = pValueBonf;
	}

	private CruxReportedPeptide reportedPeptide;
	private int scanNumber;
	private int charge;
	
	private double precursorMz;
	private double precursorNeutralMass;
	
	private double calcMassMono;
	private double calcMassAvg;
	
	private double massErrorPPM;
	
	private double xCorrScore;
	private double xCorrRank;
	private double pValue;
	
	private double ionCurrentTotal;
	
	private double qValueDecoy;
	private double qValueBH;
	
	private double fdrDecoy;
	private double fdrBH;
	
	private double pValueBonf;
	
}
