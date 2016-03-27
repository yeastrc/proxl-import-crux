package org.yeastrc.proxl.crux.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Collection;

import org.yeastrc.proxl.xml.crux.objects.CruxParams;
import org.yeastrc.proxl.xml.crux.objects.CruxResult;
import org.yeastrc.proxl.xml.crux.reader.ParamsLoader;
import org.yeastrc.proxl.xml.crux.reader.ResultsLoader;
import org.yeastrc.proxl.xml.crux.builder.*;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

public class MainProgram {

	public void convertSearch( String outputFile, File resultsDirectory, String fastaFilename, String linkerName, Double linkerMass ) throws Exception {
		
		CruxParams params = ParamsLoader.getInstance().loadParams( resultsDirectory, fastaFilename, linkerName, linkerMass );
		Collection<CruxResult> results = ResultsLoader.getInstance().loadResults( params );
		
		XMLBuilder builder = new XMLBuilder();
		builder.buildAndSaveXML(params, results, new File( outputFile ) ); 
		
	}
	
	/**
	 * Run the program.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		
		if( args.length < 1 || args[ 0 ].equals( "-h" ) ) {
			printHelp();
			System.exit( 0 );
		}
		
		CmdLineParser cmdLineParser = new CmdLineParser();
        
		CmdLineParser.Option resultsDirectoryOpt = cmdLineParser.addStringOption( 'r', "results-directory" );	
		CmdLineParser.Option fastaFilenameOpt = cmdLineParser.addStringOption( 'f', "fasta-filename" );	
		CmdLineParser.Option linkerNameOpt = cmdLineParser.addStringOption( 'l', "linker-name" );	
		CmdLineParser.Option linkerMassOpt = cmdLineParser.addDoubleOption( 'm', "linker-mass" );
		CmdLineParser.Option outputFileOpt = cmdLineParser.addStringOption( 'o', "output-file" );	

        // parse command line options
        try { cmdLineParser.parse(args); }
        catch (IllegalOptionValueException e) {
        	printHelp();
            System.exit( 1 );
        }
        catch (UnknownOptionException e) {
           printHelp();
           System.exit( 1 );
        }
        
        String resultsDirectoryString = (String)cmdLineParser.getOptionValue( resultsDirectoryOpt );
        if( resultsDirectoryString == null ) {
        	System.out.println( "-r results directory is required. Run without parameters or with -h for help." );
        	System.exit( 1 );
        }
        
        File resultsDirectory = new File( resultsDirectoryString );
        if( !resultsDirectory.exists() ) {
        	System.out.println( "Can not find results directory: " + resultsDirectory.getAbsolutePath() );
        	System.exit( 1 );
        }
        
        String fastaFilename = (String)cmdLineParser.getOptionValue( fastaFilenameOpt );
        if( fastaFilename == null ) {
        	System.out.println( "-f fastaFilename is required. Run without parameters or with -h for help." );
        	System.exit( 1 );
        }
        
        String linkerName = (String)cmdLineParser.getOptionValue( linkerNameOpt );
        if( linkerName == null ) {
        	System.out.println( "-l linkerName is required. Run without parameters or with -h for help." );
        	System.exit( 1 );
        }
        
        Double linkerMass = (Double)cmdLineParser.getOptionValue( linkerMassOpt );
        if( linkerMass == null ) {
        	System.out.println( "-m linkerMass is required. Run without parameters or with -h for help." );
        	System.exit( 1 );
        }
        
        String outputFile = (String)cmdLineParser.getOptionValue( outputFileOpt );
        if( outputFile == null ) {
        	System.out.println( "-o /path/to/output file is required. Run without parameters or with -h for help." );
        	System.exit( 1 );
        }
        
        
		MainProgram mp = new MainProgram();
		mp.convertSearch( outputFile, resultsDirectory, fastaFilename, linkerName, linkerMass );
	}
	
	public static void printHelp() {
				
		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "help.txt" ) ) ) ) {
			
			String line = null;
			while ( ( line = br.readLine() ) != null )
				System.out.println( line );				
			
		} catch ( Exception e ) {
			System.out.println( "Error printing help." );
		}
		
		
	}
	
}
