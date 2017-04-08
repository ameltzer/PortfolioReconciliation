package entry_point;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import reconciliation_loaders.ReconLoader;

public class TestReconLoaderFactory {

	/*
	 * Since this is just testing the factory, only need to check that the factory produces the correct Actual class.
	 */
	@Test
	public void testBuildTextReconLoader() throws IOException{
		ReconLoader rl = ReconLoaderFactory.buildTextReconLoader("test_data.txt");
		assertTrue(rl instanceof ReconLoader);
	}
	
}
