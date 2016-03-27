package org.yeastrc.proxl.xml.crux.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yeastrc.proxl.xml.crux.utils.NumberUtils;

/**
 * A peptide represented by crux.
 * 
 * @author Michael Riffle
 * @date Mar 26, 2016
 *
 */
public class CruxPeptide {
	
	/**
	 * Get the string representation of this peptide that includes mods, in the form of:
	 * PEP[12.2932,15.993]TI[12.2932]DE
	 */
	@Override
	public String toString() {
		
		String str = "";
		
		for( int i = 1; i <= this.getSequence().length(); i++ ) {
			String r = String.valueOf( this.getSequence().charAt( i - 1 ) );
			str += r;
			
			if( this.getMods() != null ) {
				List<String> modsAtPosition = new ArrayList<String>();
				
				if( this.getMods().get( i ) != null ) {
					for( CruxModification mod : this.getMods().get( i ) ) {
						modsAtPosition.add( NumberUtils.getRoundedBigDecimal( mod.getMass() ).toString() );
					}
					
					if( modsAtPosition.size() > 0 ) {
	
						// sort these strings on double values
						Collections.sort( modsAtPosition, new Comparator<String>() {
						       public int compare(String s1, String s2) {
						           return Double.valueOf( s1 ).compareTo( Double.valueOf( s2 ) );
						        }
						});
						
						String modsString = StringUtils.join( modsAtPosition, "," );
						str += "[" + modsString + "]";
					}
				}
			}
		}
		
		return str;
	}
	
	@Override
	public int hashCode() { return this.getSequence().hashCode(); }
	
	@Override
	public boolean equals( Object o ) {
		if( !( o instanceof CruxPeptide ) ) return false;
		return this.toString().equals( ((CruxPeptide)o).toString() );
	}
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * Mods are a map, keyed by position in peptide (starting at 1), associated
	 * with a collection of mods at that position
	 * @return
	 */
	public Map<Integer, Collection<CruxModification>> getMods() {
		return mods;
	}

	/**
	 * Mods are a map, keyed by position in peptide (starting at 1), associated
	 * with a collection of mods at that position
	 * @param mods
	 */
	public void setMods(Map<Integer, Collection<CruxModification>> mods) {
		this.mods = mods;
	}

	private String sequence;
	private Map<Integer, Collection<CruxModification>> mods;
	
}
