package com.jobdata.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;

@ExtendWith(SpringExtension.class)
public class JobManagementQueueTest {
	
	@InjectMocks
	JobManagementQueue jobManagementQueue; 
	
	@Mock
	private JobDAO jobDAO;
	
	@Test
	 public void addJobsToQueueTest() throws InterruptedException {
		 
		JobInfo lowJobInfo = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobInfo highJobInfo = new JobInfo("high", JobType.SEND_EMAIL, PriorityLevel.HIGH);
		JobInfo lowJobInfo1 = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobInfo highJobInfo1 = new JobInfo("high", JobType.SEND_EMAIL, PriorityLevel.HIGH);
		
		jobManagementQueue.addJobToQueue(lowJobInfo);
		jobManagementQueue.addJobToQueue(highJobInfo);
		jobManagementQueue.addJobToQueue(lowJobInfo1);
		jobManagementQueue.addJobToQueue(highJobInfo1);
		assertEquals(4, jobManagementQueue.size());
		jobManagementQueue.clear();
     }
	
	 @Test
	 public void testAddByPriority() throws InterruptedException {
		 
		JobInfo lowJobInfo = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobInfo highJobInfo = new JobInfo("high", JobType.SEND_EMAIL, PriorityLevel.HIGH);
		
		jobManagementQueue.addJobToQueue(lowJobInfo);
		jobManagementQueue.addJobToQueue(highJobInfo);
		assertEquals(2, jobManagementQueue.size());
        assertEquals(PriorityLevel.HIGH, jobManagementQueue.take().getPriorityLevel());
        jobManagementQueue.clear();
	 }

}
