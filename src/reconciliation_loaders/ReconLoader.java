package reconciliation_loaders;

import java.util.List;
import java.util.Map;

import models.ReconDay;
import models.Transaction;

public interface ReconLoader {

	public ReconDay loadNextDay() throws Exception;
	public List<Transaction> loadTrans() throws Exception;
	public Map<String,Double> loadPos() throws Exception;
}
