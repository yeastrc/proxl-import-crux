package org.yeastrc.proxl.xml.crux.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Some utility methods for working with numbers.
 * 
 * @author Michael Riffle
 * @date Mar 23, 2016
 *
 */
public class NumberUtils {

	public static final int NUMBER_DECIMAL_PLACES = 4;

	public static BigDecimal getRoundedBigDecimal( double value, int scale ) {
		BigDecimal bd = new BigDecimal( value );
		bd = bd.setScale( scale, RoundingMode.HALF_UP );
		
		return bd;
	}
	
	/**
	 * Get a big decimal rounded out to NUMBER_DECIMAL_PLACES places
	 * from the supplied double.
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal getRoundedBigDecimal( double value ) {
		BigDecimal bd = new BigDecimal( value );
		bd = bd.setScale( NUMBER_DECIMAL_PLACES, RoundingMode.HALF_UP );
		
		return bd;
	}
	

	public static BigDecimal getScientificNotationBigDecimal( double value ) {
		return new BigDecimal( getScientificNotation( value ) );
	}
	
	public static String getScientificNotation( double value ) {
		  NumberFormat formatter = new DecimalFormat("0.0E0");
		  formatter.setRoundingMode(RoundingMode.HALF_UP);
		  formatter.setMinimumFractionDigits( NUMBER_DECIMAL_PLACES );
		  return formatter.format( value );
	}
	
}
