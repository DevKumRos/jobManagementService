package com.jobdata.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;

@Component
public class JobManagementExecutor implements  Runnable {
	
	private Logger logger = LoggerFactory.getLogger(JobManagementExecutor.class);

    private JobInfo<String> jobInfo;
    @Autowired
    private JobManagementService jobManagementService;
    public JobManagementExecutor (JobInfo<String> jobInfo) {
    	this.jobInfo = jobInfo;
    }
    
    
  /*  public JobManagementExecutor(JobInfo<String> jobInfo, JobManagementService jobManagementService) {
    	this.jobInfo = jobInfo;
    	this.jobManagementService = jobManagementService;
    }
  */  
    /**
     * Performing action of job based on job type
     */
    @Override
    public void run() {
    	boolean jobActionStatus = false;
    	try {
    		logger.info("Job {} execution started", jobInfo.getData().toUpperCase());

    		if (JobType.SEND_EMAIL == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.sendEmail(jobInfo.getData());
    		} else if (JobType.LOAD_DATA == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.loadData(jobInfo.getData());
    		} else if (JobType.INDEX_FILES == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.indexFiles(jobInfo.getData());
    		}
    		logger.info("Job {} execution completed", jobInfo.getData().toUpperCase());
    	} catch (Exception e) {
    		logger.error("Job execution exception is occured, " + e.getMessage());
    	}finally {
    		setJobState(jobActionStatus);
    	}
    }

    private void setJobState(boolean jobActionSuccessful) {
        if (jobActionSuccessful) {
            jobInfo.getJobState().add(JobState.SUCCESS);
        } else {
            jobInfo.getJobState().add(JobState.FAILED);
        }
    }
}
