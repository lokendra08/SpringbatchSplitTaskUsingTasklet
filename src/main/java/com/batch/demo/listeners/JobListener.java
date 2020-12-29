package com.batch.demo.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.err.println("Called beforeJob() ==>"+jobExecution.getJobInstance().getJobName()+"  "+jobExecution.getJobInstance().getInstanceId());
		System.err.println("jobId is : "+jobExecution.getJobId());
		System.err.println("jobId exit status  is : "+jobExecution.getExitStatus());
		System.err.println("job status  is : "+jobExecution.getStatus());
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.err.println("Called afterJob() ==>"+jobExecution.getJobInstance().getJobName()+"  "+jobExecution.getJobInstance().getInstanceId());
		System.err.println("jobId is : "+jobExecution.getJobId());
		System.err.println("jobId exit status  is : "+jobExecution.getExitStatus().getExitCode());
		System.err.println("job status  is : "+jobExecution.getStatus());
		jobExecution.getStepExecutions().stream().forEach(s->{
			System.out.println(s.getStepName()+"  "+s.getExitStatus().getExitCode());
			
		});
		
	}

}
