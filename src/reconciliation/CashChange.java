package reconciliation;

import java.util.Map;

public class CashChange {
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange, boolean positive) {
		Double curCash = curPort.get(item);
		int modifier = (positive) ? 1 : -1;
		Double newCash = curCash + modifier * cashChange;
		curPort.put("Cash", newCash);
		return curPort;
	}
}
