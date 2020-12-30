package com.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

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
	                .start(splitFlow())
	                .build().build();
	}  
	
	@Bean
	public Flow splitFlow() {
	  return new FlowBuilder<SimpleFlow>("splitFlow")
	    .split(taskExecutor())
	    .add(flow1(),flow3())
	    .next(flow2())
	    .build();
	}

	@Bean
	public Flow flow1() {
	  return new FlowBuilder<SimpleFlow>("flow1")
	    .start(stepOne())
	    .build();
	}

	@Bean
	public Flow flow2() {
	  return new FlowBuilder<SimpleFlow>("flow2")
	    .start(stepTwo())
	    .build();
	}
	
	@Bean
	public Flow flow3() {
	  return new FlowBuilder<SimpleFlow>("flow3")
	    .start(stepThree())
	    .build();
	}

	@Bean
	public TaskExecutor taskExecutor(){
	  return new SimpleAsyncTaskExecutor("spring_batch_loki");
	}
	
	

}
