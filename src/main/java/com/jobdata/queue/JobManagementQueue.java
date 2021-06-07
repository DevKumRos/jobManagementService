package com.jobdata.queue;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jobdata.models.JobInfo;
import com.jobdata.models.PriorityLevel;

@Component
public class JobManagementQueue<T extends Serializable> extends LinkedBlockingDeque<JobInfo<T>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(JobManagementQueue.class);

    @Override
    public JobInfo<T> take() throws InterruptedException {
        JobInfo<T> takenJobInfo = super.takeFirst();
        logger.info("Job " + takenJobInfo.getData() + " is taken from head of the queue");
        return takenJobInfo;
    }

    @Override
    public boolean offerFirst(JobInfo<T> jobInfo) {
        if (jobInfo == null) {
            logger.warn("jobInfo cannot be null to added to the queue");
            return false;
        }

        if (super.offerFirst(jobInfo)) {
            logger.info("job {} is added to the queue", jobInfo.getData());
            return true;
        } else {
            logger.info("job {} is not added to the queue", jobInfo.getData());
            return false;
        }
    }

    @Override
    public boolean offerLast(JobInfo<T> jobInfo) {
        if (jobInfo == null) {
            logger.warn("jobInfo cannot be null to added to the queue");
            return false;
        }

        if (super.offerLast(jobInfo)) {
            logger.info("job {} is added to the queue", jobInfo.getData());
            return true;
        } else {
            logger.info("job {} is not added to the queue", jobInfo.getData());
            return false;
        }
    }
    
    public boolean addJobToQueue(JobInfo<T> jobInfo) {
        if (PriorityLevel.HIGH == jobInfo.getPriorityLevel()) {
            return offerFirst(jobInfo);
        }

        return offerLast(jobInfo);
    }
}
