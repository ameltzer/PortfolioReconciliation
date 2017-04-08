package reconciliation_loaders;

import java.util.List;
import java.util.Map;

import models.ReconDay;
import models.Transaction;

public interface ReconLoader {
	/**
	 * Interface defining required functionality for an input loader. Interface is defined so that loading of input and processing
	 * of input is decoupled. This allows us to create loaders for different kinds of databases, or files, or any kind of input without
	 * changing calling code
	 * @return
	 * @throws Exception
	 */
	public ReconDay loadNextDay() throws Exception;
	public List<Transaction> loadTrans() throws Exception;
	public Map<String,Double> loadPos() throws Exception;
}
