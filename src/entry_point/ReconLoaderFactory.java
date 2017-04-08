package entry_point;

import java.io.IOException;

import reconciliation_loaders.ReconLoader;
import reconciliation_loaders.TextFileLoader;

public class ReconLoaderFactory {

	public static ReconLoader buildTextReconLoader(String txtFile) throws IOException {
		TextFileLoader reconLoader = new TextFileLoader();
		reconLoader.setBr(txtFile);
		return reconLoader;
	}
}
