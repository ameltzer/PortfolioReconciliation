package reconciliation_loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import models.Actions;
import models.ReconDay;
import models.Transaction;
import reconciliation.PortfolioAction;

/**
 * Concrete implementation of ReconLoader for when the file is text. Due to the fact that text can very easily become larger than main
 * memory, the loader is designed to read in part of a file at a time. If the file is small and needs to be fully loaded, a class similar
 * to this one with a few simple changes can be made to do so. The primary change would be replacing the BufferedReader with a List of Strings,
 * and changing any code that then accesses the BufferedReader to access a List of Strings
 *
 */
public class TextFileLoader implements ReconLoader{
	
	//stores file for access
	private BufferedReader br;
	//maps transaction name to transaction class
	private Actions actions;
	//keeps track of whether next part of file is a position or transaction part
	private boolean atPos;
	
	/*
	 * Use Spring to wire together Actions object with this. Since this class will be building the ReconDay data structure, it will
	 * need to know the mapping between transaction name and transaction class. This is why the Actions object is necessary
	 * 
	 * Also creates buffered reader for passed in file
	 */
	public TextFileLoader(String file) throws IOException{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		actions = (Actions) ctx.getBean("actions");
		ctx.close();
		atPos = true;
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
		br = new BufferedReader(new InputStreamReader(is));
		br.readLine();
	}
	/**
	 * Load next day in file, which is a transaction and the position. The order is transaction then position because the initial position
	 * will be loaded in seprately. This leaves the reader at the position of a transaction.
	 */
	@Override
	public ReconDay loadNextDay() throws Exception{
		//first line will be pos - # we can ignore it		
		List<Transaction> transactions = loadTrans();
		Map<String,Double> pos = loadPos();

		
		ReconDay reconDay = new ReconDay(pos, transactions);
		return reconDay;
	}
	/**
	 * just load transaction. Throw an error if not over a transaction
	 */
	@Override
	public List<Transaction> loadTrans() throws Exception{
		if(atPos){
			throw new Exception("Must load Trans when file is over a Trans section. "
					+ "File is over Pos section");
		}
		
		List<Transaction> transactions = new ArrayList<>();
		Pattern dayRegex = Pattern.compile("D[0-9]+\\-POS");
		String line = "";
		while((line=br.readLine())!=null && !dayRegex.matcher(line).matches()){
			String[] parts = line.split(" ");
			String item = parts[0];
			PortfolioAction action = actions.getAction(parts[1]);
			Double shares = Double.parseDouble(parts[2]);
			Double cashChange = Double.parseDouble(parts[3]);
			Transaction transaction = new Transaction(item, action, shares, cashChange);
			transactions.add(transaction);
		}
		atPos=true;
		return transactions;
	}
	/**
	 * just load position. Throw an error if not over a position
	 */
	@Override
	public Map<String,Double> loadPos() throws Exception{
		if(!atPos){
			throw new Exception("Must load pos when file is over pos section. "
					+ "File is over trans section");
		}
		
		Map<String,Double> pos = new HashMap<>();
		Pattern transRegex = Pattern.compile("D[0-9]+\\-TRN");
		String line = "";
		while((line=br.readLine())!=null && !transRegex.matcher(line).matches()){
			String[] parts = line.split(" ");
			String item = parts[0];
			Double value = Double.parseDouble(parts[1]);
			pos.put(item, value);
		}
		if(!pos.containsKey("Cash")){
			pos.put("Cash", 0.0);
		}
		atPos=false;
		return pos;
	}
}
