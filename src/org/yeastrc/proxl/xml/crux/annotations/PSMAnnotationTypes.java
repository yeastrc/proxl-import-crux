package org.yeastrc.proxl.xml.crux.annotations;

import java.util.ArrayList;
import java.util.List;

import org.yeastrc.proxl.xml.crux.utils.CruxConstants;
import org.yeastrc.proxl.xml.crux.utils.NumberUtils;
import org.yeastrc.proxl_import.api.xml_dto.DescriptivePsmAnnotationType;
import org.yeastrc.proxl_import.api.xml_dto.FilterDirectionType;
import org.yeastrc.proxl_import.api.xml_dto.FilterablePsmAnnotationType;

public class PSMAnnotationTypes {

	/**
	 * Get the list of filterable PSM annotation types in Crux data
	 * @return
	 */
	public static List<FilterablePsmAnnotationType> getFilterablePsmAnnotationTypes() {
		List<FilterablePsmAnnotationType> types = new ArrayList<FilterablePsmAnnotationType>();

		//decoy q-value
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_Q_VALUE_DECOY );
			type.setDescription( "Q-value calculated using decoys." );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( true );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		/*
		//xcorr rank
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_XCORR_RANK );
			type.setDescription( "XCorr rank of this PSM for this spectrum." );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "1" ) , 0 ) );;
			type.setDefaultFilter( true );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		*/

		//xcorr
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_XCORR_SCORE );
			type.setDescription( "XCorr score." );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "1" ) , 0 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.ABOVE );
			
			types.add( type );
		}
		
		//p-value
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_P_VALUE );
			type.setDescription( "P-value" );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		//Bonferroni corrected p-value
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_P_VALUE_BONF );
			type.setDescription( "Bonferroni corrected p-value" );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		// Benjamini-Hochberg q-value
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_Q_VALUE_BH );
			type.setDescription( "Benjamini-Hochberg q-value" );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		// decoy-based FDR
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_FDR_DECOY );
			type.setDescription( "Decoy-based false discovery rate." );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		// Benjamini-Hochberg FDR
		{
			FilterablePsmAnnotationType type = new FilterablePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_FDR_BH );
			type.setDescription( "Benjamini-Hochberg false discovery rate." );
			type.setDefaultFilterValue( NumberUtils.getRoundedBigDecimal( Double.valueOf( "0.01" ) , 2 ) );;
			type.setDefaultFilter( false );
			type.setFilterDirection( FilterDirectionType.BELOW );
			
			types.add( type );
		}
		
		return types;
	}
	
	/**
	 * Get the list of descriptive (non-filterable) PSM annotation types in StavroX data
	 * @return
	 */
	public static List<DescriptivePsmAnnotationType> getDescriptivePsmAnnotationTypes() {
		List<DescriptivePsmAnnotationType> types = new ArrayList<DescriptivePsmAnnotationType>();
		
		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_PRECURSOR_MZ );
			type.setDescription( type.getName() );
			
			types.add( type );
		}

		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_PRECURSOR_MASS );
			type.setDescription( type.getName() );
			
			types.add( type );
		}
		
		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_CALC_MASS_MONO );
			type.setDescription( type.getName() );
			
			types.add( type );
		}
		
		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_CALC_MASS_AVG );
			type.setDescription( type.getName() );
			
			types.add( type );
		}
		
		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_MASS_ERROR_PPM );
			type.setDescription( type.getName() );
			
			types.add( type );
		}
		
		{
			DescriptivePsmAnnotationType type = new DescriptivePsmAnnotationType();
			type.setName( CruxConstants.COLUMN_HEADER_ION_CURRENT_TOTAL );
			type.setDescription( type.getName() );
			
			types.add( type );
		}
		
		return types;		
	}
	
}
