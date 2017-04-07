package reconciliation;

import java.util.Map;

public interface PortfolioAction {

	Map<String, Double> applyAction(Map<String, Double> curPort, String item, 
			double shares, double cashChange);

}
