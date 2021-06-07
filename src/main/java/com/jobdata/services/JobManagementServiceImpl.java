package com.jobdata.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdata.models.JobInfo;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class JobManagementServiceImpl implements JobManagementService {
    public static final long PROCESSING_TIME_IN_SECOND = 5000;
    
    @Autowired
    private Map<UUID, JobInfo> jobDB;
    
    @Override
    public boolean sendEmail(String data) {
        //log.info("SEND EMAIL STARTED");
        try {
            simulateJobAction();
            //log.info("DONE EMAIL SENT");
            return true;
        } catch (InterruptedException e) {
            log.error("Email sending is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean loadData(String data) {
        //log.info("DATA LOADING STARTED");
        try {
            simulateJobAction();
            //log.info("COMPLETED DATA LOADING");
            return true;
        } catch (InterruptedException e) {
            log.error("Data Loading is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean indexFiles(String data) {
        //log.info("FILES INDEXES STARTED");
        try {
            simulateJobAction();
            //log.info("COMPLETED FILES INDEXES");
            return true;
        } catch (InterruptedException e) {
            log.error("File indexing is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void simulateJobAction() throws InterruptedException {
        Thread.sleep(PROCESSING_TIME_IN_SECOND);
    }

	@Override
	public void saveJob(JobInfo jobInfo) {
		if(jobInfo.getJobId() == null)
			jobInfo.setJobId(UUID.randomUUID());
    	
		jobDB.put(jobInfo.getJobId(), jobInfo);
	}
    
    
}
