package reconciliation;

import java.util.Map;

public class Dividend extends CashChange implements PortfolioAction {
	/**
	 * An action resulting in an increase in cash change by the specified amount. In the text file the share change is filled in as 0
	 * and therefore we don't need to be concerned about shares changing. However item will be a ticker name, and not Cash. As such
	 * the item value must be manually changed to Cash so that Cash is the value that changes, and not shares.
	 */
	@Override
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange) {
		item = "Cash";
		Map<String,Double> newPort = this.applyAction(curPort, item, shares, cashChange, true);
		return newPort;
	}

}
