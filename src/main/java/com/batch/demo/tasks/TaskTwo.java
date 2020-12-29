package com.batch.demo.tasks;

import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskTwo implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		System.out.println("Validation started.."); 
		try {
			TimeUnit.SECONDS.sleep(2);
			int a = 2/0;
		} catch (Exception e) {
			contribution.setExitStatus(ExitStatus.FAILED);
		}
        System.out.printf("Validation completed..");
        return RepeatStatus.FINISHED;
	}

}