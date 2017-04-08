package models;

import java.util.HashMap;
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
	
	public Map<String,Double> doRecon(Map<String, Double> nextDayShouldBe){
		Map<String, Double> nextDayShouldBeCopy = new HashMap<>();
		for(String key : nextDayShouldBe.keySet()){
			nextDayShouldBeCopy.put(key, nextDayShouldBe.get(key));
		}
		for(String key : nextDayInFile.keySet()){
			if(!nextDayShouldBeCopy.containsKey(key)){
				nextDayShouldBeCopy.put(key, 0.0);
			}
		}
		
		Map<String, Double> discrepencies = new HashMap<>();
		for(String key : nextDayShouldBeCopy.keySet()){
			Double shouldBe = nextDayShouldBeCopy.get(key);
			Double inFile = (nextDayInFile.containsKey(key)) ? nextDayInFile.get(key) : 0;
			Double diff = inFile - shouldBe;
			if(diff!=0){
				discrepencies.put(key, diff);
			}
		}
		return discrepencies;
	}
	
	public Map<String,Double> getNextDayInFile(){
		return this.nextDayInFile;
	}
	public List<Transaction> getTransactions(){
		return this.transactions;
	}
	
}
