package org.yeastrc.proxl.xml.crux.utils;

import java.util.regex.Pattern;

public class CruxConstants {

	public static final String SEARCH_PROGRAM_NAME = "Crux";
	
	// all crux-identified peptides must be one of these types
	public static final int LINK_TYPE_CROSSLINK = 0;
	public static final int LINK_TYPE_MONOLINK = 1;
	public static final int LINK_TYPE_LOOPLINK = 2;
	public static final int LINK_TYPE_UNLINKED = 3;

	
	public static final String CRUX_RESULTS_FILE = "search-for-xlinks.qvalues..txt";
	public static final String CRUX_RESULTS_FILE2 = "search-for-xlinks.qvalues.txt";		// incase the naming bug gets fixed

	public static final String CRUX_LOG_FILE = "search-for-xlinks.log.txt";
	
	public static final String CRUX_PARAMS_FILE = "search-for-xlinks.params.txt";
	
	public static final String[] aminoAcids = new String[]{ "A","R","N","D","C","Q","E","G","H","I","L","K","M","F","P","S","T","W","Y","V" };
	
	public static final String COLUMN_HEADER_PEPTIDE = "sequence";
	public static final String COLUMN_HEADER_SCAN_NUMBER = "scan";
	public static final String COLUMN_HEADER_CHARGE = "charge";
	public static final String COLUMN_HEADER_PRECURSOR_MZ = "spectrum precursor m/z";
	public static final String COLUMN_HEADER_PRECURSOR_MASS = "spectrum neutral mass";
	public static final String COLUMN_HEADER_CALC_MASS_MONO = "peptide mass mono";
	public static final String COLUMN_HEADER_CALC_MASS_AVG = "peptide mass average";
	public static final String COLUMN_HEADER_MASS_ERROR_PPM = "mass error(ppm)";
	public static final String COLUMN_HEADER_XCORR_SCORE = "xcorr score";
	public static final String COLUMN_HEADER_XCORR_RANK = "xcorr rank";
	public static final String COLUMN_HEADER_P_VALUE = "p-value";
	public static final String COLUMN_HEADER_ION_CURRENT_TOTAL = "ion current total";
	public static final String COLUMN_HEADER_Q_VALUE_DECOY = "q-value decoy";
	public static final String COLUMN_HEADER_Q_VALUE_BH = "q-value b-h";
	public static final String COLUMN_HEADER_FDR_DECOY = "fdr decoy";
	public static final String COLUMN_HEADER_FDR_BH = "fdr b-h";
	public static final String COLUMN_HEADER_P_VALUE_BONF = "p-value bonf.";
	
	public static final Pattern PEPTIDE_UNLINKED_PATTERN = Pattern.compile( "([A-Z]+)\\(\\)" );
	public static final Pattern PEPTIDE_MONOLINK_PATTERN = Pattern.compile( "([A-Z]+)\\((\\d+)\\)" );
	public static final Pattern PEPTIDE_LOOPLINK_PATTERN = Pattern.compile( "([A-Z]+)\\((\\d+)\\,(\\d+)\\)" );
	public static final Pattern PEPTIDE_CROSSLINK_PATTERN = Pattern.compile( "([A-Z]+)\\,([A-Z]+)\\((\\d+)\\,(\\d+)\\)" );
	
}
