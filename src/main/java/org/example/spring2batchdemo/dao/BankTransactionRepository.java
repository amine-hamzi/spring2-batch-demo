package org.example.spring2batchdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long>{

}
