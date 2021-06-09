package com.jobdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobManagementExecutor implements  Runnable {
	
	private JobInfo jobInfo;
    @Autowired
    private JobManagementService jobManagementService;
    @Autowired
    private JobDAO jobDAO;
    
    public JobManagementExecutor () {
    }
    public JobManagementExecutor (JobInfo jobInfo) {
    	this.jobInfo = jobInfo;
    }
    
    
    public JobManagementExecutor(JobInfo jobInfo, JobManagementService jobManagementService,JobDAO jobDAO ) {
    	this.jobInfo = jobInfo;
    	this.jobManagementService = jobManagementService;
    	this.jobDAO = jobDAO;
    }
   
    /**
     * Performing action of job based on job type
     */
    @Override
    public void run() {
    	boolean jobActionStatus = false;
    	jobInfo.getJobState().add(JobState.RUNNING);
    	jobDAO.saveJob(jobInfo);
    	try {
    		log.info("Job {} execution started", jobInfo.getData().toUpperCase());

    		if (JobType.SEND_EMAIL == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.sendEmail(jobInfo.getData());
    		} else if (JobType.LOAD_DATA == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.loadData(jobInfo.getData());
    		} else if (JobType.INDEX_FILES == jobInfo.getJobType()) {
    			jobActionStatus = jobManagementService.indexFiles(jobInfo.getData());
    		}
    		log.info("Job {} execution completed", jobInfo.getData().toUpperCase());
    	} catch (Exception e) {
    		log.error("Job execution exception is occured, " + e.getMessage());
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
        jobDAO.saveJob(jobInfo);
    }
}
