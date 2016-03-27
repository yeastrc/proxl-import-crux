package org.yeastrc.proxl.xml.crux.objects;

import org.yeastrc.proxl.xml.crux.utils.CruxConstants;

public class CruxReportedPeptide {

	/**
	 * A string representation of this reported peptide, where a reported peptide is the combination
	 * of peptide sequence(s), linked position(s), and dynamic mods in each respective peptide. We could
	 * use the strings reported directly from Crux, as it does uniquely identify this combination of
	 * attributes as of this writing. However, we are applying our own syntax so that the mass of the
	 * linker may be displayed as a dynamic mod, for information purposes within Proxl.
	 * 
	 * We are also doing this to future proof this code, in the event that Crux starts allowing dynamic
	 * mods in its crosslink searches.
	 */
	@Override
	public String toString() {

		if( this.type == CruxConstants.LINK_TYPE_CROSSLINK ) {
			return this.getPeptide1().toString() + "(" + this.getPosition1() + ")-" +
				   this.getPeptide2().toString() + "(" + this.getPosition2() + ")";
		} else if( this.type == CruxConstants.LINK_TYPE_LOOPLINK ) {
			return this.getPeptide1().toString() + "(" + this.getPosition1() + "," +
				   this.getPosition2() + ")";
		} else if( this.type == CruxConstants.LINK_TYPE_MONOLINK || this.type == CruxConstants.LINK_TYPE_UNLINKED ) {
			return this.getPeptide1().toString();
		}
		
		return "Error, unknown type... (" + this.getPeptide1().toString() + ")";
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals( Object o ) {
		if( !(o instanceof CruxReportedPeptide ) ) return false;
		
		return this.toString().equals( ((CruxReportedPeptide)o).toString() );
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public CruxPeptide getPeptide1() {
		return peptide1;
	}
	public void setPeptide1(CruxPeptide peptide1) {
		this.peptide1 = peptide1;
	}
	public CruxPeptide getPeptide2() {
		return peptide2;
	}
	public void setPeptide2(CruxPeptide peptide2) {
		this.peptide2 = peptide2;
	}
	public int getPosition1() {
		return position1;
	}
	public void setPosition1(int position1) {
		this.position1 = position1;
	}
	public int getPosition2() {
		return position2;
	}
	public void setPosition2(int position2) {
		this.position2 = position2;
	}
	
	private int type;
	private CruxPeptide peptide1;
	private CruxPeptide peptide2;
	private int position1;
	private int position2;
	
	
}
