package com.jobdata.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class JobManagementServiceImpl implements JobManagementService {
    private Logger logger = LoggerFactory.getLogger(JobManagementServiceImpl.class);
    public static final long PROCESSING_TIME_IN_SECOND = 500;
    
    @Override
    public boolean sendEmail(String data) {
        //logger.info("SEND EMAIL STARTED");
        try {
            simulateJobAction();
            //logger.info("DONE EMAIL SENT");
            return true;
        } catch (InterruptedException e) {
            logger.error("Email sending is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean loadData(String data) {
        //logger.info("DATA LOADING STARTED");
        try {
            simulateJobAction();
            //logger.info("COMPLETED DATA LOADING");
            return true;
        } catch (InterruptedException e) {
            logger.error("Data Loading is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean indexFiles(String data) {
        //logger.info("FILES INDEXES STARTED");
        try {
            simulateJobAction();
            //logger.info("COMPLETED FILES INDEXES");
            return true;
        } catch (InterruptedException e) {
            logger.error("File indexing is failed, exception: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private void simulateJobAction() throws InterruptedException {
        Thread.sleep(PROCESSING_TIME_IN_SECOND);
    }
    
    
}
