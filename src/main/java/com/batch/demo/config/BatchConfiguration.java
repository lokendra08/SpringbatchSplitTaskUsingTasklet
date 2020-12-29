package com.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.demo.listeners.JobListener;
import com.batch.demo.listeners.TaskThreeListener;
import com.batch.demo.tasks.TaskOne;
import com.batch.demo.tasks.TaskThree;
import com.batch.demo.tasks.TaskTwo;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public Step stepOne() {
		return steps.get("CacheInitializeStep")
				    .tasklet(new TaskOne())
				    .listener(new TaskThreeListener())
				    .build();
	}
	
	@Bean
	public Step stepTwo() {
		return steps.get("ValidationStep")
				    .tasklet(new TaskTwo())
				    .listener(new TaskThreeListener())
				    .build();
	}
	
	@Bean
	public Step stepThree() {
		return steps.get("ConfigInitializeStep")
				    .tasklet(new TaskThree())
				    .listener(new TaskThreeListener())
				    .build();
	}
	
	@Bean
	public Job demoJob(){
	     return jobs.get("LokiJob")
	                .incrementer(new RunIdIncrementer())
	                .listener(new JobListener())
	                .start(stepOne())
	                .next(stepTwo())
	                .next(stepThree())
	                .build();
	}            

}
