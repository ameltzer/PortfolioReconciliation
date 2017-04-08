package reconciliation;

import java.util.Map;

public class Buy extends BuySell implements PortfolioAction {

	@Override
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange) {
		Map<String, Double> newPort = this.applyAction(curPort, item, shares, cashChange,true);
		return newPort;
	}

}
