package entry_point;

import java.io.IOException;

import reconciliation_loaders.ReconLoader;
import reconciliation_loaders.TextFileLoader;

/**
 * 
 * Factory pattern for producing Loader based on data input type
 *
 */
public class ReconLoaderFactory {

	/**
	 * Returns loader for text files. Takes the text file to load
	 * @param txtFile
	 * @return
	 * @throws IOException
	 */
	public static ReconLoader buildTextReconLoader(String txtFile) throws IOException {
		TextFileLoader reconLoader = new TextFileLoader(txtFile);
		return reconLoader;
	}
}
