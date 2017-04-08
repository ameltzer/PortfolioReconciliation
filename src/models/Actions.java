package models;

import java.util.Map;

import reconciliation.PortfolioAction;

/**
 * 
 * small class to hold mapping from a transactions String (i.e buy or sell) to the corresponding Class.
 *
 */
public class Actions {
	
	private Map<String, PortfolioAction> nameToAction;
	
	public Actions(Map<String, PortfolioAction> nameToAction){
		this.nameToAction = nameToAction;
	}
	
	public PortfolioAction getAction(String name){
		return nameToAction.get(name);
	}
}
