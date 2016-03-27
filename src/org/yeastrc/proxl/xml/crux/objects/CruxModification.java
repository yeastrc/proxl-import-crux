package org.yeastrc.proxl.xml.crux.objects;

/**
 * A modification represented by Crux.
 * 
 * @author Michael Riffle
 * @date Mar 26, 2016
 *
 */
public class CruxModification {
	
	/**
	 * The mass of the mod. This is usually going to be the monoisotopic mass, but
	 * these values are passed in by users to crux at run-time as the only mass
	 * value associated with a mod--so we don't really know.
	 * @return
	 */
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public boolean isMonolink() {
		return isMonolink;
	}

	public void setMonolink(boolean isMonolink) {
		this.isMonolink = isMonolink;
	}



	private double mass;
	private boolean isMonolink = false;
}
