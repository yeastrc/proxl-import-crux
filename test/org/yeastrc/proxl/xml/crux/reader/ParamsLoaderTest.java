package org.yeastrc.proxl.xml.crux.reader;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParamsLoaderTest {

	@Test
	public void testFindAndReturnVersion() {
		String line = "INFO: Crux version: 2.1";
		
		assertEquals( ParamsLoader.getInstance().findAndReturnVersion( line ), "2.1" );
	}

	@Test
	public void testFindAndReturnCmdLine() {
		String line = "COMMAND LINE: ./crux search-for-xlinks --cmod 57.02146:C:100:F:F ../stavrox/Q_2013_1010_RJ_07.mzML ../stavrox/gTuSC-parsimonious-plusRev.fasta K:K,nterm:K 138.0680742";
		
		assertEquals( ParamsLoader.getInstance().findAndReturnCmdLine( line ),
				"./crux search-for-xlinks --cmod 57.02146:C:100:F:F ../stavrox/Q_2013_1010_RJ_07.mzML ../stavrox/gTuSC-parsimonious-plusRev.fasta K:K,nterm:K 138.0680742" );
	}

}
