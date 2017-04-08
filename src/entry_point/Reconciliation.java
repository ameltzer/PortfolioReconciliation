package entry_point;

import java.util.Map;

import models.ReconDay;
import reconciliation_loaders.ReconLoader;

public class Reconciliation {
	
	/*
	 * start code
	 */
	public static void main(String[] args){
		try {
			ReconLoader loader = ReconLoaderFactory.buildTextReconLoader("test_data.txt");
			(new Reconciliation()).startRecon(loader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Simply print out differences uncovered by program
	 * @param disc
	 */
	public  void printDisc(Map<String,Double> disc){
		for(String key : disc.keySet()){
			System.out.println(key+":"+disc.get(key));
		}
	}
	
	/**
	 * starts off the recon. Load the initial position, and then move onto rest of transactions and days. Takes in ReconLoader, an interface
	 * that defines a function for loading either a single position, single transaction, or a transaction and next day combination.
	 * @param loader
	 * @throws Exception
	 */
	public void startRecon(ReconLoader loader) throws Exception{
		Map<String, Double> initialPos = loader.loadPos();
		
		currentDayDisc(initialPos, loader);
	}
	
	/**
	 * This code is isolated in its own function from the code that gets the initial position because if the code were ever to be
	 * extended past 2 days and one transaction, this is the code that would have be looped over for each new transaction and resulting
	 * position
	 * @param initialPos
	 * @param loader
	 * @throws Exception
	 */
	public void currentDayDisc(Map<String,Double> initialPos,ReconLoader loader) 
			throws Exception{
		ReconDay recon = loader.loadNextDay();
		Map<String,Double> posShouldBe = recon.applyTransactions(initialPos);
		Map<String,Double> discrepencies = recon.doRecon(posShouldBe);
		printDisc(discrepencies);
	}
}
