package org.example.spring2batchdemo;

import lombok.Getter;
import org.example.spring2batchdemo.dao.BankTransaction;
import org.springframework.batch.item.ItemProcessor;

//@Component // cette methode est stateless ce qui signifie qu'elle fait des choses mais ne garde rien
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor<BankTransaction,BankTransaction>{
	
    @Getter private double totalDebit;   //ces attributs definissent l'etat du processor BankTransactionItemAnalyticsProcessor. youssfi dit qu'il a une memoire
    @Getter private double totalCredit;  //
    

	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws Exception {

		if(bankTransaction.getTransactionType().equals("D"))totalDebit += bankTransaction.getAmount();		
		else if(bankTransaction.getTransactionType().equals("C"))totalCredit += bankTransaction.getAmount();		
		
		return bankTransaction;
	}

}
