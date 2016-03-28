package org.yeastrc.proxl.xml.crux.annotations;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.proxl.xml.crux.utils.CruxConstants;
import org.yeastrc.proxl_import.api.xml_dto.SearchAnnotation;

public class PSMAnnotationTypeSortOrder {

	public static List<SearchAnnotation> getPSMAnnotationTypeSortOrder() {
		List<SearchAnnotation> annotations = new ArrayList<SearchAnnotation>();
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_Q_VALUE_DECOY );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		
		
		{
			SearchAnnotation annotation = new SearchAnnotation();
			annotation.setAnnotationName( CruxConstants.COLUMN_HEADER_P_VALUE_BONF );
			annotation.setSearchProgram( CruxConstants.SEARCH_PROGRAM_NAME );
			annotations.add( annotation );
		}
		
		return annotations;
	}
}
