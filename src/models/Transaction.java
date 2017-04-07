package models;

import java.util.Map;

import reconciliation.PortfolioAction;

public class Transaction {
	//is either a ticker or cash
	private String item;
	//interface represents a possible action
	private PortfolioAction action;
	private double shares;
	private double cashChange;

	
	public Transaction(String item, PortfolioAction action,
			double shares, double cashChange){
		this.item= item;
		this.action = action;
		this.shares = shares;
		this.cashChange = cashChange;
	}

	public Map<String,Double> applyAction(Map<String,Double> curPort){
		Map<String,Double> dayShouldBe = action.applyAction(curPort, item, shares, cashChange);
		return dayShouldBe;
	}
	
	public String getItem() {
		return item;
	}

	public PortfolioAction getAction() {
		return action;
	}

	public double getShares() {
		return shares;
	}

	public double getCashChange() {
		return cashChange;
	}
	
	
}
