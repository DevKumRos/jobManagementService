package com.jobdata.services;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;
import com.jobdata.queue.JobManagementQueue;

@Component
public class JobManagmentProccesor{
	
	private Logger logger = LoggerFactory.getLogger(JobManagmentProccesor.class);
	
	@Autowired
	private ScheduledExecutorService executorService;
	
	@Autowired
	private JobManagementQueue<String> jobQueue;
	
	@Autowired
	private JobManagementService jobManagementService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Scheduled(fixedDelay=20000)
	public void startProssingJob() {
		try {
			while (jobQueue.size() > 0 && !Thread.currentThread().isInterrupted()) {
                JobInfo<String> takenJobInfo = jobQueue.take();
                if (takenJobInfo.getJobType() == JobType.EXIT) {
                    logger.info("JOB COMPLETED SUCCESSFULLY");
                    takenJobInfo.getJobState().add(JobState.SUCCESS);
                    break;
                }
                runJobExecutor(takenJobInfo);
            }
        } catch (InterruptedException e) {
            logger.error(" JobManagmentProccesor exception: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("JobManagmentProccesor exception is occured, " + e.getMessage());
        }
		
	}

	private void runJobExecutor(final JobInfo<String> takenJobInfo) {
        takenJobInfo.getJobState().add(JobState.RUNNING);
        JobManagementExecutor jobExecutor =  (JobManagementExecutor) applicationContext.getBean("jobManagementExecutor", takenJobInfo);
        executorService.schedule(jobExecutor, takenJobInfo.getDelay(), TimeUnit.MILLISECONDS);
    }
}
