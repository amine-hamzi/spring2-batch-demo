package org.example.spring2batchdemo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Optional;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class BankTransaction {
	
	@Id
	private Long id;
	private long accountID;
	private Date transactionDate;
	@Transient
	private String strTransactionDate;
	private String transactionType;
	private double amount;

	public static void main(String[] args) {
		Optional<String> c = Optional.of("");
		var cc = Optional.empty();
		if(c.isPresent()) System.out.println(c.get());
	}
	

}
