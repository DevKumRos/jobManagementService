package com.jobdata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;
import com.jobdata.queue.JobManagementQueue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobManagmentProccesorTest {
	
	@Autowired
	JobManagementQueue jobManagementQueue; 
	
	@Mock
	JobDAO jobDAO;
	
	@InjectMocks
	JobManagmentProccesor jobManagmentProccesor;
	
	@Mock
	JobManagementServiceImpl jobManagementService;
	
	@Test
	public void startProcessTest_SUCCESS() throws InterruptedException {
		JobInfo lowJobInfo = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		jobManagementQueue.addJobToQueue(lowJobInfo);
		Thread.sleep(6000);
		assertEquals(3, lowJobInfo.getJobState().size());
		assertEquals(JobState.SUCCESS, lowJobInfo.getJobState().get(2));
	}
	
	@Test
	public void startProcessTest_FAILED() throws InterruptedException {
		JobInfo lowJobInfo = new JobInfo(null, JobType.SEND_EMAIL, PriorityLevel.LOW);
		jobManagementQueue.addJobToQueue(lowJobInfo);
		jobManagmentProccesor.startProssingJob();
		Thread.sleep(6000);
		assertEquals(3, lowJobInfo.getJobState().size());
		assertEquals(JobState.FAILED, lowJobInfo.getJobState().get(2));
	}

}
