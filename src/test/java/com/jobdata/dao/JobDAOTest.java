package com.jobdata.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jobdata.configuration.JobConfiguration;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;

@ExtendWith(SpringExtension.class)
public class JobDAOTest {
	
	@InjectMocks
	private JobDAO jobDAO;
	
	
	@Test
	public void savJob_SUCCESS() {
		JobInfo jobInfo = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobInfo jobInfo1 = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.HIGH);
		jobDAO.saveJob(jobInfo);
		jobDAO.saveJob(jobInfo1);
		assertEquals(2, jobDAO.numberofJobInQueue());
	}
	
	@Test
	public void getALLJob_QUEUE() {
		JobInfo jobInfo = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobInfo jobInfo1 = new JobInfo("low", JobType.SEND_EMAIL, PriorityLevel.HIGH);
		jobDAO.saveJob(jobInfo);
		jobDAO.saveJob(jobInfo1);
		assertEquals(1, jobDAO.getAllJob().get(jobInfo.getJobId()).getJobState().size());
		assertEquals(JobState.QUEUED, jobDAO.getAllJob().get(jobInfo.getJobId()).getJobState().get(0));
	}
	

}
