package reconciliation;

import java.util.Map;

public class CashChange {
	/**
	 * Common code for purely cash changes. Accepts an extra boolean parameter positive that states whether cash change is going up
	 * or down
	 * @param curPort
	 * @param item
	 * @param shares
	 * @param cashChange
	 * @param positive
	 * @return
	 */
	public Map<String, Double> applyAction(Map<String, Double> curPort, String item, double shares, double cashChange, boolean positive) {
		Double curCash = curPort.get(item);
		int modifier = (positive) ? 1 : -1;
		Double newCash = curCash + modifier * cashChange;
		curPort.put("Cash", newCash);
		return curPort;
	}
}
