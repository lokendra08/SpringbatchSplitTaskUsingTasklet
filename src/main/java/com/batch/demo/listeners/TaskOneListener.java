package com.batch.demo.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class TaskOneListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
        System.out.println("Called beforeStep() ===> " +stepExecution.getStepName());	
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.printf("Called afterStep() ===> " +stepExecution.getStepName());
		if(stepExecution.getStatus()==BatchStatus.FAILED){
			System.out.println(" ");
			System.err.println("Exception occurs at step" +stepExecution.getStepName());
		}
		return null;
	}
}