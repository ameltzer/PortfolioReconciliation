package reconciliation;

import java.util.Map;

public interface PortfolioAction {
	/**
	 * common interface defining information needed to make a change to the portfolio and to define a transaction. All existing
	 * and new transactions should implement this interface
	 * @param curPort
	 * @param item
	 * @param shares
	 * @param cashChange
	 * @return
	 */
	Map<String, Double> applyAction(Map<String, Double> curPort, String item, 
			double shares, double cashChange);

}
