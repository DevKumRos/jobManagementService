package com.jobdata.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class JobManagementServiceImpl implements JobManagementService {
	@Value(value="${job.processing.time}")
    public long PROCESSING_TIME_IN_SECOND;
    
    @Override
    public boolean sendEmail(String data) {
        //log.info("SEND EMAIL STARTED");
    	try {
        	simulateJobAction(data);
            //log.info("DONE EMAIL SENT");
            return true;
        } catch (InterruptedException | RuntimeException e) {
            log.error("Email sending is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean loadData(String data) {
        //log.info("DATA LOADING STARTED");
    	if(data == null)
    		return false;
        try {
            simulateJobAction(data);
            //log.info("COMPLETED DATA LOADING");
            return true;
        } catch (InterruptedException | RuntimeException e) {
            log.error("Data Loading is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean indexFiles(String data) {
        //log.info("FILES INDEXES STARTED");
    	if(data == null)
    		return false;
        try {
            simulateJobAction(data);
            //log.info("COMPLETED FILES INDEXES");
            return true;
        } catch (InterruptedException | RuntimeException e) {
            log.error("File indexing is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void simulateJobAction(String data) throws InterruptedException {
        Thread.sleep(PROCESSING_TIME_IN_SECOND);
        data.toString();
    }

	
    
    
}
