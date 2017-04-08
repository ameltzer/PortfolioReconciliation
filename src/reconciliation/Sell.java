package reconciliation;

import java.util.Map;

public class Sell extends BuySell implements PortfolioAction {
	/**
	 * This is the sell action, the shares will decrease by the specified amount and cash increase by the specified amount. The code
	 * is very similar to Buy and therefore abstracted to the parent class that accepts one more parameter, which is asking if
	 * the shares should increase ore decrease and same with cash change
	 */
	@Override
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange) {
		Map<String, Double> newPort = this.applyAction(curPort, item, shares, cashChange,false);
		return newPort;
	}

}
