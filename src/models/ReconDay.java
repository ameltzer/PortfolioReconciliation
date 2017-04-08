package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Holds nth transaction and nth position. nth position should be result of applying nth transaction to n-1th position
 *
 */
public class ReconDay {
	//map representing position
	private Map<String,Double> nextDayInFile;
	//list of transactions to apply on the previous day's position (not the position in nextDayInFile)
	private List<Transaction> transactions;
	
	public ReconDay(Map<String,Double> itemToAmount, 
			List<Transaction> transactions){
		this.nextDayInFile = itemToAmount;
		this.transactions = transactions;
	}
	
	/**
	 * Take in a position and apply every transaction. In current program the input will be the previous day's position.
	 * @param nextDayShouldBe
	 * @return
	 */
	public Map<String,Double> applyTransactions(Map<String,Double> nextDayShouldBe){		
		for(Transaction transaction : transactions){
			nextDayShouldBe = transaction.applyAction(nextDayShouldBe);
		}
		
		return nextDayShouldBe;
	}
	
	/**
	 * Reconciles what the user says the nextDayShouldBe, and what was read in from the file. In current program the input
	 * will be the result of applyTransaction on the previous day's position
	 * @param nextDayShouldBe
	 * @return
	 */
	public Map<String,Double> doRecon(Map<String, Double> nextDayShouldBe){
		Map<String, Double> nextDayShouldBeCopy = new HashMap<>();
		/*
		 * because nextDayShouldBe contents will be modified, and are passed in by reference, we want to make a copy so that
		 * the calling code of this function doesn't encounter anything unexpected after returning from this function.
		 */
		for(String key : nextDayShouldBe.keySet()){
			nextDayShouldBeCopy.put(key, nextDayShouldBe.get(key));
		}
		/*
		 * There are two edge cases to keep in mind:
		 * When a key is in nextDayInFile but not nextDayShouldBe, and when a key is in nextDayShouldBe but not nextDayInFile
		 * 
		 * Because the loop will ultimately be driving from nextDayShouldBeCopy, the latter case will still be taken into account
		 * (the loop will encounter the key in nextDayShouldBeCopy, and the fact that its not in nextDayInFile will be taken to mean
		 * it should be 0)
		 * However without any alterations, the former case will not be considered because the loop will not encounter it. Because
		 * a key->value where value=0 is equivalent to the key value pair not being in the map, a way to fix the situation is by
		 * putting the missing key into nextDayShouldBeCopy, but with a value of 0.
		 */
		for(String key : nextDayInFile.keySet()){
			if(!nextDayShouldBeCopy.containsKey(key)){
				nextDayShouldBeCopy.put(key, 0.0);
			}
		}
		/*
		 * Do the recon
		 */
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
