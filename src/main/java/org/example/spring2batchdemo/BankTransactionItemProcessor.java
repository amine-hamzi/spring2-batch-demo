package org.example.spring2batchdemo;

import org.example.spring2batchdemo.dao.BankTransaction;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;

//@Component  // en enlevant cette annotation cela signifie qu'il n'est pas instancié
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction>{
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
	

	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws Exception {

		bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));		
		
		return bankTransaction;
	}

}
