package org.example.spring2batchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBatchDemoApplication implements CommandLineRunner {

	@Autowired
    JobLauncher jobLauncher;
	@Autowired
    Job job;


    public static void main(String[] args) {
        SpringApplication.run(SpringBatchDemoApplication.class, args);
    }

//
//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Starting the batch job");
//		try {
//			JobExecution execution = jobLauncher.run(job, new JobParameters());
//			System.out.println("Job Status : " + execution.getStatus());
//			System.out.println("Job completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Job failed");
//		}
//	}

	@Override
	public void run(String... args) throws Exception {

		JobParameters jobParameters =
				new JobParametersBuilder()
						.addLong("time", System.currentTimeMillis())
						.toJobParameters();

		jobLauncher.run(job, jobParameters);
		System.out.println("JOB Execution completed!");
	}
}
