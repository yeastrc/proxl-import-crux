package org.yeastrc.proxl.xml.crux.builder;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proxl.xml.crux.annotations.PSMAnnotationTypeSortOrder;
import org.yeastrc.proxl.xml.crux.annotations.PSMAnnotationTypes;
import org.yeastrc.proxl.xml.crux.annotations.PSMDefaultVisibleAnnotationTypes;
import org.yeastrc.proxl.xml.crux.objects.CruxModification;
import org.yeastrc.proxl.xml.crux.objects.CruxParams;
import org.yeastrc.proxl.xml.crux.objects.CruxReportedPeptide;
import org.yeastrc.proxl.xml.crux.objects.CruxResult;
import org.yeastrc.proxl.xml.crux.utils.CruxConstants;
import org.yeastrc.proxl.xml.crux.utils.NumberUtils;
import org.yeastrc.proxl_import.api.xml_dto.AnnotationSortOrder;
import org.yeastrc.proxl_import.api.xml_dto.ConfigurationFile;
import org.yeastrc.proxl_import.api.xml_dto.ConfigurationFiles;
import org.yeastrc.proxl_import.api.xml_dto.CrosslinkMass;
import org.yeastrc.proxl_import.api.xml_dto.CrosslinkMasses;
import org.yeastrc.proxl_import.api.xml_dto.DecoyLabel;
import org.yeastrc.proxl_import.api.xml_dto.DecoyLabels;
import org.yeastrc.proxl_import.api.xml_dto.DefaultVisibleAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotationTypes;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotationTypes;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.LinkType;
import org.yeastrc.proxl_import.api.xml_dto.LinkedPosition;
import org.yeastrc.proxl_import.api.xml_dto.LinkedPositions;
import org.yeastrc.proxl_import.api.xml_dto.Linker;
import org.yeastrc.proxl_import.api.xml_dto.Linkers;
import org.yeastrc.proxl_import.api.xml_dto.Modification;
import org.yeastrc.proxl_import.api.xml_dto.Modifications;
import org.yeastrc.proxl_import.api.xml_dto.Peptide;
import org.yeastrc.proxl_import.api.xml_dto.Peptides;
import org.yeastrc.proxl_import.api.xml_dto.ProxlInput;
import org.yeastrc.proxl_import.api.xml_dto.Psm;
import org.yeastrc.proxl_import.api.xml_dto.PsmAnnotationSortOrder;
import org.yeastrc.proxl_import.api.xml_dto.Psms;
import org.yeastrc.proxl_import.api.xml_dto.ReportedPeptide;
import org.yeastrc.proxl_import.api.xml_dto.ReportedPeptides;
import org.yeastrc.proxl_import.api.xml_dto.SearchAnnotation;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgram;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgramInfo;
import org.yeastrc.proxl_import.api.xml_dto.SearchPrograms;
import org.yeastrc.proxl_import.api.xml_dto.StaticModification;
import org.yeastrc.proxl_import.api.xml_dto.StaticModifications;
import org.yeastrc.proxl_import.api.xml_dto.VisiblePsmAnnotations;
import org.yeastrc.proxl_import.api.xml_dto.SearchProgram.PsmAnnotationTypes;
import org.yeastrc.proxl_import.create_import_file_from_java_objects.main.CreateImportFileFromJavaObjectsMain;

/**
 * Save the populated crux objects as proxl xml
 * 
 * @author Michael Riffle
 * @date Mar 27, 2016
 *
 */
public class XMLBuilder {

	public void buildAndSaveXML( CruxParams params, Collection<CruxResult> results, File outfile ) throws Exception {
		
		ProxlInput proxlInputRoot = new ProxlInput();
		
		proxlInputRoot.setFastaFilename( params.getFastaFilename() );
		
		SearchProgramInfo searchProgramInfo = new SearchProgramInfo();
		proxlInputRoot.setSearchProgramInfo( searchProgramInfo );
		
		SearchPrograms searchPrograms = new SearchPrograms();
		searchProgramInfo.setSearchPrograms( searchPrograms );
		
		SearchProgram searchProgram = new SearchProgram();
		searchPrograms.getSearchProgram().add( searchProgram );
		
		searchProgram.setName( CruxConstants.SEARCH_PROGRAM_NAME );
		searchProgram.setDisplayName( CruxConstants.SEARCH_PROGRAM_NAME );

		if( params.getVersion() != null )
			searchProgram.setVersion( params.getVersion() );
		
		if( params.getCommandLineRun() != null )
			proxlInputRoot.setComment( "Command line: " + params.getCommandLineRun() );
		
		//
		// Define the annotation types present in PLink data
		//
		PsmAnnotationTypes psmAnnotationTypes = new PsmAnnotationTypes();
		searchProgram.setPsmAnnotationTypes( psmAnnotationTypes );
		
		FilterablePsmAnnotationTypes filterablePsmAnnotationTypes = new FilterablePsmAnnotationTypes();
		psmAnnotationTypes.setFilterablePsmAnnotationTypes( filterablePsmAnnotationTypes );
		filterablePsmAnnotationTypes.getFilterablePsmAnnotationType().addAll( PSMAnnotationTypes.getFilterablePsmAnnotationTypes() );
		
		DescriptivePsmAnnotationTypes descriptivePsmAnnotationTypes = new DescriptivePsmAnnotationTypes();
		psmAnnotationTypes.setDescriptivePsmAnnotationTypes( descriptivePsmAnnotationTypes );
		descriptivePsmAnnotationTypes.getDescriptivePsmAnnotationType().addAll( PSMAnnotationTypes.getDescriptivePsmAnnotationTypes() );

		//
		// Define which annotation types are visible by default
		//
		DefaultVisibleAnnotations xmlDefaultVisibleAnnotations = new DefaultVisibleAnnotations();
		searchProgramInfo.setDefaultVisibleAnnotations( xmlDefaultVisibleAnnotations );
		
		VisiblePsmAnnotations xmlVisiblePsmAnnotations = new VisiblePsmAnnotations();
		xmlDefaultVisibleAnnotations.setVisiblePsmAnnotations( xmlVisiblePsmAnnotations );

		xmlVisiblePsmAnnotations.getSearchAnnotation().addAll( PSMDefaultVisibleAnnotationTypes.getDefaultVisibleAnnotationTypes() );
		
		
		//
		// Define the sort order
		//
		AnnotationSortOrder annotationSortOrder = new AnnotationSortOrder();
		searchProgramInfo.setAnnotationSortOrder( annotationSortOrder );
		
		PsmAnnotationSortOrder psmAnnotationSortOrder = new PsmAnnotationSortOrder();
		annotationSortOrder.setPsmAnnotationSortOrder( psmAnnotationSortOrder );
		
		psmAnnotationSortOrder.getSearchAnnotation().addAll( PSMAnnotationTypeSortOrder.getPSMAnnotationTypeSortOrder() );
		

		
		//
		// Define the linker information
		//
		Linkers linkers = new Linkers();
		proxlInputRoot.setLinkers( linkers );

		Linker linker = new Linker();
		linkers.getLinker().add( linker );
		
		linker.setName( params.getLinkerName() );
		
		CrosslinkMasses masses = new CrosslinkMasses();
		linker.setCrosslinkMasses( masses );
		
		CrosslinkMass xlinkMass = new CrosslinkMass();
		linker.getCrosslinkMasses().getCrosslinkMass().add( xlinkMass );
		
		xlinkMass.setMass( NumberUtils.getRoundedBigDecimal( params.getLinkerMass() ) );
		
		//
		// Define the static mods
		//
		StaticModifications smods = new StaticModifications();
		proxlInputRoot.setStaticModifications( smods );
		
		for( String residue : params.getStaticMods().keySet() ) {
			StaticModification xmlSmod = new StaticModification();
			xmlSmod.setAminoAcid( residue );
			xmlSmod.setMassChange( NumberUtils.getRoundedBigDecimal( params.getStaticMods().get( residue ) ) );
				
			smods.getStaticModification().add( xmlSmod );
		}
		
		//
		// Add decoy labels (optional)
		//
		DecoyLabels xmlDecoyLabels = new DecoyLabels();
		proxlInputRoot.setDecoyLabels( xmlDecoyLabels );
		
		{
			DecoyLabel xmlDecoyLabel = new DecoyLabel();
			xmlDecoyLabels.getDecoyLabel().add( xmlDecoyLabel );
			
			xmlDecoyLabel.setPrefix( "random" );
		}
		
		{
			DecoyLabel xmlDecoyLabel = new DecoyLabel();
			xmlDecoyLabels.getDecoyLabel().add( xmlDecoyLabel );
			
			xmlDecoyLabel.setPrefix( "decoy" );
		}
		
		
		
		
		//
		// Define the peptide and PSM data
		//
		ReportedPeptides reportedPeptides = new ReportedPeptides();
		proxlInputRoot.setReportedPeptides( reportedPeptides );
		
		// need to organize all results by distinct reported peptide strings
		Map<CruxReportedPeptide, Collection<CruxResult>> resultsByReportedPeptide = new HashMap<CruxReportedPeptide, Collection<CruxResult>>();
		for( CruxResult result : results ) {
			
			if( !resultsByReportedPeptide.containsKey( result.getReportedPeptide() ) )
				resultsByReportedPeptide.put( result.getReportedPeptide(), new ArrayList<CruxResult>() );
			
			resultsByReportedPeptide.get( result.getReportedPeptide() ).add( result );
		}
		
		
		// iterate over each distinct reported peptide
		for( CruxReportedPeptide rp : resultsByReportedPeptide.keySet() ) {
			
			ReportedPeptide xmlReportedPeptide = new ReportedPeptide();
			reportedPeptides.getReportedPeptide().add( xmlReportedPeptide );
			
			xmlReportedPeptide.setReportedPeptideString( rp.toString() );
			
			if( rp.getType() == CruxConstants.LINK_TYPE_CROSSLINK )
				xmlReportedPeptide.setType( LinkType.CROSSLINK );
			else if( rp.getType() == CruxConstants.LINK_TYPE_LOOPLINK )
				xmlReportedPeptide.setType( LinkType.LOOPLINK );
			else
				xmlReportedPeptide.setType( LinkType.UNLINKED );	// monolinked peptide with no cross- or loop-link are considered unlinked (monolinks are considered mods)
			
			Peptides xmlPeptides = new Peptides();
			xmlReportedPeptide.setPeptides( xmlPeptides );
			
			// add in the 1st parsed peptide
			{
				Peptide xmlPeptide = new Peptide();
				xmlPeptides.getPeptide().add( xmlPeptide );
				
				xmlPeptide.setSequence( rp.getPeptide1().getSequence() );
				
				// add in the mods for this peptide
				if( rp.getPeptide1().getMods() != null && rp.getPeptide1().getMods().keySet().size() > 0 ) {
					
					Modifications xmlModifications = new Modifications();
					xmlPeptide.setModifications( xmlModifications );
					
					for( int position : rp.getPeptide1().getMods().keySet() ) {
						for( CruxModification mod : rp.getPeptide1().getMods().get( position ) ) {
	
							Modification xmlModification = new Modification();
							xmlModifications.getModification().add( xmlModification );
							
							xmlModification.setMass( NumberUtils.getRoundedBigDecimal( mod.getMass() ) );
							xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );
							xmlModification.setIsMonolink( mod.isMonolink() );
							
						}
					}
				}
				
				// add in the linked position(s) in this peptide
				if( rp.getType() == CruxConstants.LINK_TYPE_CROSSLINK || rp.getType() == CruxConstants.LINK_TYPE_LOOPLINK ) {
					
					LinkedPositions xmlLinkedPositions = new LinkedPositions();
					xmlPeptide.setLinkedPositions( xmlLinkedPositions );
					
					LinkedPosition xmlLinkedPosition = new LinkedPosition();
					xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
					xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition1() ) ) );
					
					if( rp.getType() == CruxConstants.LINK_TYPE_LOOPLINK ) {
						
						xmlLinkedPosition = new LinkedPosition();
						xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
						xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition2() ) ) );
						
					}
				}
				
			}
			
			
			// add in the 2nd parsed peptide, if it exists
			if( rp.getPeptide2() != null ) {
				
				Peptide xmlPeptide = new Peptide();
				xmlPeptides.getPeptide().add( xmlPeptide );
				
				xmlPeptide.setSequence( rp.getPeptide2().getSequence() );
				
				// add in the mods for this peptide
				if( rp.getPeptide2().getMods() != null && rp.getPeptide2().getMods().keySet().size() > 0 ) {
					
					Modifications xmlModifications = new Modifications();
					xmlPeptide.setModifications( xmlModifications );
					
					for( int position : rp.getPeptide2().getMods().keySet() ) {
						for( CruxModification mod : rp.getPeptide2().getMods().get( position ) ) {

							Modification xmlModification = new Modification();
							xmlModifications.getModification().add( xmlModification );
							
							xmlModification.setMass( NumberUtils.getRoundedBigDecimal( mod.getMass() ) );
							xmlModification.setPosition( new BigInteger( String.valueOf( position ) ) );
							xmlModification.setIsMonolink( mod.isMonolink() );
							
						}
					}
				}
				
				// add in the linked position in this peptide
				if( rp.getType() == CruxConstants.LINK_TYPE_CROSSLINK ) {
					
					LinkedPositions xmlLinkedPositions = new LinkedPositions();
					xmlPeptide.setLinkedPositions( xmlLinkedPositions );
					
					LinkedPosition xmlLinkedPosition = new LinkedPosition();
					xmlLinkedPositions.getLinkedPosition().add( xmlLinkedPosition );
					xmlLinkedPosition.setPosition( new BigInteger( String.valueOf( rp.getPosition2() ) ) );
				}
			}
			
			
			// add in the PSMs and annotations
			Psms xmlPsms = new Psms();
			xmlReportedPeptide.setPsms( xmlPsms );
			
			// iterate over all PSMs for this reported peptide
			for( CruxResult result : resultsByReportedPeptide.get( rp ) ) {
				Psm xmlPsm = new Psm();
				xmlPsms.getPsm().add( xmlPsm );
				
				xmlPsm.setScanNumber( new BigInteger( String.valueOf( result.getScanNumber() ) ) );
				xmlPsm.setPrecursorCharge( new BigInteger( String.valueOf( result.getCharge() ) ) );
				
				if( rp.getType() == CruxConstants.LINK_TYPE_CROSSLINK || rp.getType() == CruxConstants.LINK_TYPE_LOOPLINK )
					xmlPsm.setLinkerMass( NumberUtils.getRoundedBigDecimal( params.getLinkerMass() ) );
				
				// add in the filterable PSM annotations (e.g., score)
				FilterablePsmAnnotations xmlFilterablePsmAnnotations = new FilterablePsmAnnotations();
				xmlPsm.setFilterablePsmAnnotations( xmlFilterablePsmAnnotations );
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_Q_VALUE_DECOY );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getqValueDecoy() ) );
				}
				
				/*
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_XCORR_RANK );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( result.getxCorrRank(), 1 ) );
				}
				*/
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_XCORR_SCORE );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( result.getxCorrScore() ) );
				}
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_P_VALUE );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getpValue() ) );
				}
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_P_VALUE_BONF );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getpValueBonf() ) );
				}
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_Q_VALUE_BH );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getqValueBH() ) );
				}
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_FDR_DECOY );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getFdrDecoy() ) );
				}
				
				{
					FilterablePsmAnnotation xmlFilterablePsmAnnotation = new FilterablePsmAnnotation();
					xmlFilterablePsmAnnotations.getFilterablePsmAnnotation().add( xmlFilterablePsmAnnotation );
					
					xmlFilterablePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_FDR_BH );
					xmlFilterablePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					xmlFilterablePsmAnnotation.setValue( NumberUtils.getScientificNotationBigDecimal( result.getFdrBH() ) );
				}
				

				
				
				// add in the non-filterable descriptive annotations (e.g., calculated mass)
				DescriptivePsmAnnotations xmlDescriptivePsmAnnotations = new DescriptivePsmAnnotations();
				xmlPsm.setDescriptivePsmAnnotations( xmlDescriptivePsmAnnotations );
				
				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_PRECURSOR_MZ );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getPrecursorMz() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getPrecursorMz() ) );
					}
				}

				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_PRECURSOR_MASS );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getPrecursorNeutralMass() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getPrecursorNeutralMass() ) );
					}
				}
				
				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_CALC_MASS_MONO );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getCalcMassMono() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getCalcMassMono() ) );
					}
				}
				
				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_CALC_MASS_AVG );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getCalcMassAvg() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getCalcMassAvg() ) );
					}
				}
				
				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_MASS_ERROR_PPM );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getMassErrorPPM() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getMassErrorPPM() ) );
					}
				}
				
				{
					DescriptivePsmAnnotation xmlDescriptivePsmAnnotation = new DescriptivePsmAnnotation();
					xmlDescriptivePsmAnnotations.getDescriptivePsmAnnotation().add( xmlDescriptivePsmAnnotation );
					
					xmlDescriptivePsmAnnotation.setAnnotationName( CruxConstants.COLUMN_HEADER_ION_CURRENT_TOTAL );
					xmlDescriptivePsmAnnotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
					
					// try to limit this value to the chosen number of decimal places
					try {
						xmlDescriptivePsmAnnotation.setValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( result.getIonCurrentTotal() ) ).toString() );
					} catch( Exception e ) {
						xmlDescriptivePsmAnnotation.setValue( String.valueOf( result.getIonCurrentTotal() ) );
					}
				}

				
				
			}//end iterating over all PSMs for a reported peptide
			
			
		}// end iterating over distinct reported peptides
		
		
		// add in the config file(s)
		ConfigurationFiles xmlConfigurationFiles = new ConfigurationFiles();
		proxlInputRoot.setConfigurationFiles( xmlConfigurationFiles );
		
		ConfigurationFile xmlConfigurationFile = new ConfigurationFile();
		xmlConfigurationFiles.getConfigurationFile().add( xmlConfigurationFile );
		
		xmlConfigurationFile.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
		xmlConfigurationFile.setFileName( CruxConstants.CRUX_PARAMS_FILE );
		xmlConfigurationFile.setFileContent( params.getParamFileContents() );
		
		//make the xml file
		CreateImportFileFromJavaObjectsMain.getInstance().createImportFileFromJavaObjectsMain(outfile, proxlInputRoot);
		
	}
	
}
