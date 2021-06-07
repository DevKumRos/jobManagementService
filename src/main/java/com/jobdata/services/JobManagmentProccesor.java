package com.jobdata.services;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;
import com.jobdata.queue.JobManagementQueue;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobManagmentProccesor{
	
	@Autowired
	private ScheduledExecutorService executorService;
	
	@Autowired
	private JobManagementQueue jobQueue;
	
	@Autowired
	private JobManagementService jobManagementService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Scheduled(fixedDelay=20000)
	public void startProssingJob() {
		try {
			while (jobQueue.size() > 0 && !Thread.currentThread().isInterrupted()) {
                JobInfo takenJobInfo = jobQueue.take();
                if (takenJobInfo.getJobType() == JobType.EXIT) {
                    log.info("JOB COMPLETED SUCCESSFULLY");
                    takenJobInfo.getJobState().add(JobState.SUCCESS);
                    jobManagementService.saveJob(takenJobInfo);
                    break;
                }
                runJobExecutor(takenJobInfo);
            }
        } catch (InterruptedException e) {
            log.error(" JobManagmentProccesor exception: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("JobManagmentProccesor exception is occured, " + e.getMessage());
        }
		
	}

	private void runJobExecutor(final JobInfo takenJobInfo) {
        JobManagementExecutor jobExecutor =  (JobManagementExecutor) applicationContext.getBean("jobManagementExecutor", takenJobInfo);
        executorService.schedule(jobExecutor, takenJobInfo.getDelay(), TimeUnit.MILLISECONDS);
    }
}
