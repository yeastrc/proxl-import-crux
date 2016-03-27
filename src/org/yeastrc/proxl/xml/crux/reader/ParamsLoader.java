package org.yeastrc.proxl.xml.crux.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration2.INIConfiguration;
import org.yeastrc.proxl.xml.crux.objects.CruxParams;
import org.yeastrc.proxl.xml.crux.utils.CruxConstants;
import org.yeastrc.proxl.xml.crux.utils.INIUtils;

/**
 * Load params associated with a crux analysis
 * 
 * @author Michael Riffle
 * @date Mar 27, 2016
 *
 */
public class ParamsLoader {

	public static ParamsLoader getInstance() { return _INSTANCE; }
	private static final ParamsLoader _INSTANCE = new ParamsLoader();
	private ParamsLoader() { }
	
	
	private Pattern versionPattern = Pattern.compile( ".+Crux version:\\s+(.+)$" );
	private Pattern cmdLinePattern = Pattern.compile( "COMMAND LINE: (.+)$" );
	
	/**
	 * Create and return a populated CruxParams with proxl-relevant parameters of this crux search-for-xlinks run.
	 * 
	 * @param cruxAnalysisDirectory The directory in which the crux analysis can be found
	 * @param fastaFilename The name (without the path) of the fasta file.
	 * @param linkerName The proxl name for the crosslinker used (e.g., "dss")
	 * @param linkerMass The linker mass provided to search-for-xlinks on the command line (choosing not to parse this from the log file)
	 * @return
	 * @throws Exception
	 */
	public CruxParams loadParams( File cruxAnalysisDirectory, String fastaFilename, String linkerName, double linkerMass ) throws Exception {
		CruxParams params = new CruxParams();
		
		params.setFastaFilename( fastaFilename );
		params.setLinkerMass( linkerMass );
		params.setLinkerName( linkerName );
		params.setAnalysisDirectory( cruxAnalysisDirectory );

		processLogFile( params );
		processParamsFile( params );		
		
		return params;
	}
	
	/**
	 * Get the contents of and the static mods from the params file.
	 * 
	 * @param params
	 * @throws Exception
	 */
	private void processParamsFile( CruxParams params ) throws Exception {
		
		File paramsFile = new File( params.getAnalysisDirectory(), CruxConstants.CRUX_PARAMS_FILE );
		if( !paramsFile.exists() ) {
			throw new Exception( CruxConstants.CRUX_PARAMS_FILE + " not found in " + params.getAnalysisDirectory() );
		}
		
		params.setParamFileContents( Files.readAllBytes( FileSystems.getDefault().getPath( paramsFile.getAbsolutePath() ) ) );
		
		// add in the static mods
		INIConfiguration iniConfig = INIUtils.getINIConfiguration( paramsFile.getAbsolutePath() );
		
		for( String aa : CruxConstants.aminoAcids ) {
			Double mass = iniConfig.getDouble( aa );
			if( mass != null && mass != 0 ) {
				
				if( params.getStaticMods() == null )
					params.setStaticMods( new HashMap<String, Double>() );
				
				params.getStaticMods().put( aa, mass );
			}				
		}
	}
	
	/**
	 * Get the version and cmd line to run crux from the log file.
	 * 
	 * @param params
	 * @throws Exception
	 */
	private void processLogFile( CruxParams params ) throws Exception {
		
		File logFile = new File( params.getAnalysisDirectory(), CruxConstants.CRUX_LOG_FILE );
		if( !logFile.exists() )
			throw new Exception( CruxConstants.CRUX_LOG_FILE + " not found in " + params.getAnalysisDirectory() );
		
		BufferedReader br = null;
		try {
			
			br = new BufferedReader( new FileReader( logFile ) );
			
			// try to find the command line or version number in the first 10 lines
			for( int i = 0; i < 10; i++ ) {

				if( params.getCommandLineRun() != null && params.getVersion() != null )
					break;
				
				String line = br.readLine();
				if( line == null ) break;
				
				if( params.getVersion() == null ) {
					String s = findAndReturnVersion( line );
					if( s != null ) {
						params.setVersion( s );
						continue;
					}
				}
				
				if( params.getCommandLineRun() == null ) {
					String s = findAndReturnCmdLine( line );
					if( s != null ) {
						params.setCommandLineRun( s );
						continue;
					}
				}				
			}
			
			
		} finally {
			try {
				if( br != null ) { br.close(); }
			} catch( Exception e ) { ; }
		}
		
	}
	
	/**
	 * Attempt to find and return the crux version number from the supplied string
	 * @param line
	 * @return The version number, if found. Null if not.
	 */
	protected String findAndReturnVersion( String line ) {
		Matcher m = versionPattern.matcher( line );
		
		if( m.matches() )
			return m.group( 1 );
		
		return null;
	}
	
	/**
	 * Attempt to find and return the command line string from the supplied string
	 * @param line
	 * @return The version number, if found. Null if not.
	 */
	protected String findAndReturnCmdLine( String line ) {
		Matcher m = cmdLinePattern.matcher( line );
		
		if( m.matches() )
			return m.group( 1 );
		
		return null;
	}
	
	
}
