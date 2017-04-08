package reconciliation;

import java.util.Map;

public class Dividend extends CashChange implements PortfolioAction {

	@Override
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange) {
		item = "Cash";
		Map<String,Double> newPort = this.applyAction(curPort, item, shares, cashChange, true);
		return newPort;
	}

}
