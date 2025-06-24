package org.example.spring2batchdemo;

import org.example.spring2batchdemo.dao.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing // permt de charger un exsemble de lignes de spring batch comme JobBuilderFactory et StepBuilderFactory
public class SpringBatchConfig {
	
	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
	@Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
	//@Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;


	@Bean // sans ca l'objet ne sera pas instancié au demarrage
	public Job bankJob() {
		Step step1 = stepBuilderFactory.get("step-load-data")
				    .<BankTransaction,BankTransaction>chunk(100) // il faut absolument specifier le diamant <BankTransaction,BankTransaction>
				    .reader(bankTransactionItemReader)
				 //   .processor(bankTransactionItemProcessor)
				    .processor(compositeItemProcessor())//compositeItemProcessor est un composite (on dit aussi pipeline), c'est une liste chainée de plusieurs processeurs
				    .writer(bankTransactionItemWriter)
				    .build();
		
		return jobBuilderFactory.get("bank-data-loader-job")
				.start(step1).build();	
		
	}
	
	
	@Bean
	public ItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor() {
		List<ItemProcessor<BankTransaction,BankTransaction>> itemProcessors = new ArrayList<>();
		itemProcessors.add(itemProcessor1());
		itemProcessors.add(itemProcessor2());
		
		CompositeItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor = new CompositeItemProcessor<>();
		compositeItemProcessor.setDelegates(itemProcessors);
		
		return compositeItemProcessor;
		
		
	}
	
	
	@Bean
	public BankTransactionItemProcessor itemProcessor1() {
		return new BankTransactionItemProcessor(); // ce processor va convertir la date au bon format
	}

	
	@Bean
	public BankTransactionItemAnalyticsProcessor itemProcessor2() {
		return new BankTransactionItemAnalyticsProcessor();
	}
	
	
	
	
	
	

	//test
	@Bean
	public FlatFileItemReader<BankTransaction> flatFileItemReader(@Value("${inputFile}") Resource inputFile){// @Value("${inputFile}")  permet d'injecter une valeur qui provient du fichier application.properties, l'annotation @Value permet d'aller chercher dans le fichier de ressources
		FlatFileItemReader<BankTransaction>fileItemReader = new FlatFileItemReader<BankTransaction>();
		fileItemReader.setName("FFIR1");
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setResource(inputFile);
		fileItemReader.setLineMapper(lineMapper());
		
		return fileItemReader;
		
				
	}

	public LineMapper<BankTransaction> lineMapper() {

		DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<BankTransaction>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id","accountID","strTransactionDate","transactionType","amount");
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(BankTransaction.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
		
	}
	
	
//	public ItemProcessor<BankTransaction,BankTransaction> itemProcessor(){
//		return new ItemProcessor<BankTransaction,BankTransaction>(){
//
//			@Override
//			public BankTransaction process(BankTransaction bankTransaction) throws Exception {
//
//				
//				
//				
//				return null;
//			}
//			
//		}
//		
//	}
	
	

}
