package models;

import java.util.List;
import java.util.Map;

public class ReconDay {
	private Map<String,Double> nextDayInFile;
	private List<Transaction> transactions;
	
	public ReconDay(Map<String,Double> itemToAmount, 
			List<Transaction> transactions){
		this.nextDayInFile = itemToAmount;
		this.transactions = transactions;
	}
	
	
	public Map<String,Double> applyTransactions(Map<String,Double> nextDayShouldBe){		
		for(Transaction transaction : transactions){
			nextDayShouldBe = transaction.applyAction(nextDayShouldBe);
		}
		
		return nextDayShouldBe;
	}
	
	public Map<String,Double> getNextDayInFile(){
		return this.nextDayInFile;
	}
	public List<Transaction> getTransactions(){
		return this.transactions;
	}
	
}
