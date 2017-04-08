package models;

import java.util.Map;

import reconciliation.PortfolioAction;

public class Transaction {
	//is either a ticker or cash
	private String item;
	//interface represents a possible action
	private PortfolioAction action;
	//number of shares that will be affected
	private double shares;
	//cash change
	private double cashChange;

	
	public Transaction(String item, PortfolioAction action,
			double shares, double cashChange){
		this.item= item;
		this.action = action;
		this.shares = shares;
		this.cashChange = cashChange;
	}

	/*
	 * apply the action that was filled into the action variable to the parameter. It's a simple wrapper function but it allows
	 * us to have a single data structure apply a transaction without needing multiple action variables. Any action can be stored
	 * in the action variable. The primary reason for this is every action must implement the interface of PortfolioAction, and
	 * therefore every action has a common parent type of PortfolioAction
	 */
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
