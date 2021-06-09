package com.jobdata.queue;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.PriorityLevel;
import com.jobdata.service.JobManagementService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobManagementQueue extends LinkedBlockingDeque<JobInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private JobDAO jobDAO;
	
    @Override
    public JobInfo take() throws InterruptedException {
        JobInfo takenJobInfo = super.takeFirst();
        log.info("Job " + takenJobInfo.getData() + " is taken from head of the queue");
        return takenJobInfo;
    }

    @Override
    public boolean offerFirst(JobInfo jobInfo) {
        if (jobInfo == null) {
            log.warn("jobInfo cannot be null to added to the queue");
            return false;
        }

        if (super.offerFirst(jobInfo)) {
            log.info("job {} is added to the queue", jobInfo.getData());
            return true;
        } else {
            log.info("job {} is not added to the queue", jobInfo.getData());
            return false;
        }
    }

    @Override
    public boolean offerLast(JobInfo jobInfo) {
        if (jobInfo == null) {
            log.warn("jobInfo cannot be null to added to the queue");
            return false;
        }

        if (super.offerLast(jobInfo)) {
            log.info("job {} is added to the queue", jobInfo.getData());
            return true;
        } else {
            log.info("job {} is not added to the queue", jobInfo.getData());
            return false;
        }
    }
    
    public boolean addJobToQueue(JobInfo jobInfo) {
    	jobDAO.saveJob(jobInfo);
        if (PriorityLevel.HIGH == jobInfo.getPriorityLevel()) {
            return offerFirst(jobInfo);
        }

        return offerLast(jobInfo);
    }
}
