package reconciliation;

import java.util.Map;

public class Deposit extends CashChange implements PortfolioAction {
	/**
	 * An action resulting in an increase in cash change by the specified amount. In the text file the share change is filled in as 0
	 * and therefore we don't need to be concerend about shares changing.
	 */
	@Override
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange) {
		Map<String,Double> newPort = this.applyAction(curPort, item, shares, cashChange, true);
		return newPort;
	}

}
