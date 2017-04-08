package reconciliation;

import java.util.Map;

public class BuySell {
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
