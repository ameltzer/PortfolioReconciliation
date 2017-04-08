package reconciliation;

import java.util.Map;

public class BuySell {
	/**
	 * Generic code for buys or sells. Extra boolean parameter buy or sell determines if modifier is 1 or -1.
	 * Code is setup so that a modifier of 1 results in shares increasing and cash decrease, and a modifier of -1
	 * results in shares decreasing and cash increasing.
	 * @param curPort
	 * @param item
	 * @param shares
	 * @param cashChange
	 * @param buy
	 * @return
	 */
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange,boolean buy) {
		Double curValue = (curPort.containsKey(item)) ? curPort.get(item) : 0;
		int modifier = (buy) ? 1 : -1;
		Double newValue = curValue + modifier * shares;
		
		Double curCash = curPort.get("Cash");
		Double newCash = curCash - modifier * cashChange;
		
		curPort.put(item, newValue);
		curPort.put("Cash", newCash);
		return curPort;
	}
}
