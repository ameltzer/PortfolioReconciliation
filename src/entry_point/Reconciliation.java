package entry_point;

import java.util.Map;

import models.ReconDay;
import reconciliation_loaders.ReconLoader;

public class Reconciliation {
	
	public static void main(String[] args){
		try {
			ReconLoader loader = ReconLoaderFactory.buildTextReconLoader("test_data.txt");
			(new Reconciliation()).startRecon(loader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void printDisc(Map<String,Double> disc){
		for(String key : disc.keySet()){
			System.out.println(key+":"+disc.get(key));
		}
	}
	
	public void startRecon(ReconLoader loader) throws Exception{
		Map<String, Double> initialPos = loader.loadPos();
		
		currentDayDisc(initialPos, loader);
	}
	public void currentDayDisc(Map<String,Double> initialPos,ReconLoader loader) 
			throws Exception{
		ReconDay recon = loader.loadNextDay();
		Map<String,Double> posShouldBe = recon.applyTransactions(initialPos);
		Map<String,Double> discrepencies = recon.doRecon(posShouldBe);
		printDisc(discrepencies);
	}
}
