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

public class TextFileLoader implements ReconLoader{
	
	private BufferedReader br;	
	private Actions actions;
	private boolean atPos;
	
	public TextFileLoader() throws IOException{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		actions = (Actions) ctx.getBean("actions");
		ctx.close();
		atPos = true;
	}
	
	public void setBr(String file) throws IOException{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
		br = new BufferedReader(new InputStreamReader(is));
		br.readLine();
	}
	
	@Override
	public ReconDay loadNextDay() throws Exception{
		//first line will be pos - # we can ignore it		
		List<Transaction> transactions = loadTrans();
		Map<String,Double> pos = loadPos();

		
		ReconDay reconDay = new ReconDay(pos, transactions);
		return reconDay;
	}
	
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
