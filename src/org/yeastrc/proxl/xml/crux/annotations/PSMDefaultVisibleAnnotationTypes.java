package org.yeastrc.proxl.xml.crux.annotations;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.proxl.xml.crux.utils.CruxConstants;
import org.yeastrc.proxl_import.api.xml_dto.SearchAnnotation;

public class PSMDefaultVisibleAnnotationTypes {

	/**
	 * Get the default visibile annotation types for StavroX data
	 * @return
	 */
	public static List<SearchAnnotation> getDefaultVisibleAnnotationTypes() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_Q_VALUE_DECOY );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		
		/*
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_XCORR_RANK );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		*/
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_P_VALUE_BONF );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_PRECURSOR_MZ );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_MASS_ERROR_PPM );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}

		
		return annotations;
	}
	
}
