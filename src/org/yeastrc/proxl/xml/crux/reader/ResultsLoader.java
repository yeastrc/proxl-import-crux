package org.yeastrc.proxl.xml.crux.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.yeastrc.proxl.xml.crux.objects.CruxModification;
import org.yeastrc.proxl.xml.crux.objects.CruxParams;
import org.yeastrc.proxl.xml.crux.objects.CruxPeptide;
import org.yeastrc.proxl.xml.crux.objects.CruxReportedPeptide;
import org.yeastrc.proxl.xml.crux.objects.CruxResult;
import org.yeastrc.proxl.xml.crux.utils.CruxConstants;

public class ResultsLoader {

	public static ResultsLoader getInstance() { return _INSTANCE; }
	private static final ResultsLoader _INSTANCE = new ResultsLoader();
	private ResultsLoader() { }
	
	public Collection<CruxResult> loadResults( CruxParams params ) throws Exception {
		Collection<CruxResult> results = new ArrayList<CruxResult>();
		
		File resultsFile = new File( params.getAnalysisDirectory(), CruxConstants.CRUX_RESULTS_FILE );
		if( !resultsFile.exists() )
			resultsFile = new File( params.getAnalysisDirectory(), CruxConstants.CRUX_RESULTS_FILE2 );
		
		if( !resultsFile.exists() )
			throw new Exception( "Unable to find results file " + CruxConstants.CRUX_RESULTS_FILE + " in " + params.getAnalysisDirectory() );
		
		try( BufferedReader br = new BufferedReader( new FileReader( resultsFile ) ) ) {
			
			// Create an index of the column headers for easy lookup of values
			String line = br.readLine();
			String[] fields = line.split( "\t" );
			Map<String, Integer> columnIndex = new HashMap<String, Integer>();
			for( int i = 0; i < fields.length; i++ ) {
				columnIndex.put( fields[ i ], i );
			}

			while( ( line = br.readLine() ) != null ) {
				fields = line.split( "\t" );
				
				CruxResult result = new CruxResult();
				
				result.setScanNumber( Integer.parseInt( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_SCAN_NUMBER ) ] ) );
				result.setCharge( Integer.parseInt( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_CHARGE ) ] ) );
				
				result.setCalcMassAvg( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_CALC_MASS_AVG ) ] ) );
				result.setCalcMassMono( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_CALC_MASS_MONO ) ] ) );
				result.setFdrBH( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_FDR_BH ) ] ) );
				result.setFdrDecoy( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_FDR_DECOY ) ] ) );
				result.setIonCurrentTotal( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_ION_CURRENT_TOTAL ) ] ) );
				result.setMassErrorPPM( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_MASS_ERROR_PPM ) ] ) );
				result.setPrecursorMz( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_PRECURSOR_MZ ) ] ) );
				result.setPrecursorNeutralMass( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_PRECURSOR_MASS ) ] ) );
				result.setpValue( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_P_VALUE ) ] ) );
				result.setpValueBonf( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_P_VALUE_BONF ) ] ) );
				result.setqValueBH( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_Q_VALUE_BH ) ] ) );
				result.setqValueDecoy( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_Q_VALUE_DECOY ) ] ) );
				result.setxCorrRank( Integer.parseInt( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_XCORR_RANK ) ] ) );
				result.setxCorrScore( Double.parseDouble( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_XCORR_SCORE ) ] ) );
				
				
				// get the repoted peptide object and set it
				result.setReportedPeptide( getReportedPeptide( fields[ columnIndex.get( CruxConstants.COLUMN_HEADER_PEPTIDE ) ], params ) );
				
				results.add( result );
			}
						
		}
		
		
		return results;
	}
	

	private CruxReportedPeptide getReportedPeptide( String sequence, CruxParams params ) throws Exception {
		
		CruxReportedPeptide rp = new CruxReportedPeptide();
		
		Matcher m = CruxConstants.PEPTIDE_UNLINKED_PATTERN.matcher( sequence );
		if( m.matches() ) {
			
			rp.setType( CruxConstants.LINK_TYPE_UNLINKED );

			sequence = m.group( 1 );
			
			CruxPeptide peptide = new CruxPeptide();
			peptide.setSequence( sequence );
			
			rp.setPeptide1( peptide );
			
			return rp;			
		}
		
		m = CruxConstants.PEPTIDE_MONOLINK_PATTERN.matcher( sequence );
		if( m.matches() ) {
			
			rp.setType( CruxConstants.LINK_TYPE_MONOLINK );

			sequence = m.group( 1 );
			
			int position = Integer.parseInt( m.group( 2 ) );
			
			CruxPeptide peptide = new CruxPeptide();
			peptide.setSequence( sequence );
			
			CruxModification mod = new CruxModification();
			mod.setMass( params.getLinkerMass() );
			mod.setMonolink( true );
			
			Map<Integer, Collection<CruxModification>> mods = new HashMap<Integer, Collection<CruxModification>>();
			mods.put( position,  new ArrayList<CruxModification>() );
			mods.get( position ).add( mod );
			
			peptide.setMods( mods );
			
			rp.setPeptide1( peptide );
			rp.setPosition1( position );
			
			return rp;
		}
		
		m = CruxConstants.PEPTIDE_LOOPLINK_PATTERN.matcher( sequence );
		if( m.matches() ) {
			
			rp.setType( CruxConstants.LINK_TYPE_LOOPLINK );

			sequence = m.group( 1 );
			
			int position1 = Integer.parseInt( m.group( 2 ) );
			int position2 = Integer.parseInt( m.group( 3 ) );

			int tPos;
			if( position1 > position2 ) {
				tPos = position1;
				position1 = position2;
				position2 = tPos;
			}
			
			CruxPeptide peptide = new CruxPeptide();
			peptide.setSequence( sequence );
			
			rp.setPeptide1( peptide );
			rp.setPosition1( position1 );
			rp.setPosition2( position2 );
			
			return rp;
		}
		
		m = CruxConstants.PEPTIDE_CROSSLINK_PATTERN.matcher( sequence );
		if( m.matches() ) {
			
			rp.setType( CruxConstants.LINK_TYPE_CROSSLINK );

			String sequence1 = m.group( 1 );
			String sequence2 = m.group( 2 );
			
			int position1 = Integer.parseInt( m.group( 3 ) );
			int position2 = Integer.parseInt( m.group( 4 ) );
			
			// sort sequences in a consistent way
			if( sequence1.compareTo( sequence2 ) > 0 ) {
				String tmpSequence = sequence1;
				int tmpPosition =  position1;
				
				sequence1 = sequence2;
				sequence2 = tmpSequence;
				
				position1 = position2;
				position2 = tmpPosition;
			} else if( sequence1.equals( sequence2 ) && position1 > position2 ) {
				int tmpPosition =  position1;

				position1 = position2;
				position2 = tmpPosition;
			}
			
			CruxPeptide peptide = new CruxPeptide();
			peptide.setSequence( sequence1 );
			
			rp.setPeptide1( peptide );
			
			peptide = new CruxPeptide();
			peptide.setSequence( sequence2 );
			
			rp.setPeptide2( peptide );
			
			rp.setPosition1( position1 );
			rp.setPosition2( position2 );
			
			return rp;
		}
		
		
		throw new Exception( "Error getting reported peptide, unknown syntax for peptide: " + sequence );
		
	}

	
	
	
}
