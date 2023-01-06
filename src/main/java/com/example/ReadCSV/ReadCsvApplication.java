package com.example.ReadCSV;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.batch.core.launch.JobLauncher;

@SpringBootApplication
public class ReadCsvApplication  implements CommandLineRunner {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job generateReportCard;

	public static void main(String[] args) {
		SpringApplication.run(ReadCsvApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		JobParameters params = new JobParametersBuilder().addString("JobID",
						String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(generateReportCard, params);
	}
}
