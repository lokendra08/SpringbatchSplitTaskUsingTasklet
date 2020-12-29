package com.batch.demo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
	
	@Autowired
    private JobLauncher jobLauncher;
     
    @Autowired
    private Job job;
    
    @RequestMapping(value="/jobone", method= {RequestMethod.GET}, produces={MediaType.APPLICATION_JSON_VALUE})
	public void runJob1(){
    	JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        try {
			jobLauncher.run(job, params);
		} catch (JobExecutionAlreadyRunningException 
				 | JobRestartException 
				 | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

}
