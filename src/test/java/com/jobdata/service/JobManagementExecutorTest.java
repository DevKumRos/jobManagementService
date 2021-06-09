package com.jobdata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobState;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;

@ExtendWith(SpringExtension.class)
public class JobManagementExecutorTest {
	
	@Mock
	JobDAO jobDAO;
	@Mock
	JobManagementService jobManagementService;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@Test
	public void fileIndexJobExecute_Success() throws InterruptedException {
		JobInfo fileIndexJob = new JobInfo("file index", JobType.INDEX_FILES, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(fileIndexJob, jobManagementService, jobDAO);
		Mockito.when(jobManagementService.indexFiles(fileIndexJob.getData())).thenReturn(true);
		jobExecutor.run();
		assertEquals(3, fileIndexJob.getJobState().size());
		assertEquals(fileIndexJob.getJobState().get(2), JobState.SUCCESS);
	}
	
	@Test
	public void fileIndexJobExecute_Failure() throws InterruptedException {
		JobInfo fileIndexJob = new JobInfo("file index", JobType.INDEX_FILES, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(fileIndexJob, jobManagementService, jobDAO);
		jobExecutor.run();
		assertEquals(3, fileIndexJob.getJobState().size());
		assertEquals(fileIndexJob.getJobState().get(2), JobState.FAILED);
	}

	@Test
	public void sendEmailJobExecute_Success() throws InterruptedException {
		JobInfo sendEmailJob = new JobInfo("send email", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(sendEmailJob, jobManagementService, jobDAO);
		Mockito.when(jobManagementService.sendEmail(sendEmailJob.getData())).thenReturn(true);
		
		jobExecutor.run();
		assertEquals(3, sendEmailJob.getJobState().size());
		assertEquals(sendEmailJob.getJobState().get(2), JobState.SUCCESS);
	}
	
	@Test
	public void sendEmailJobExecute_Failed() throws InterruptedException {
		JobInfo sendEmailJob = new JobInfo("send email", JobType.SEND_EMAIL, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(sendEmailJob, jobManagementService, jobDAO);
		jobExecutor.run();
		assertEquals(3, sendEmailJob.getJobState().size());
		assertEquals(sendEmailJob.getJobState().get(2), JobState.FAILED);
	}
	
	@Test
	public void LoadDataJobExecute_Success() throws InterruptedException {
		JobInfo loadData = new JobInfo("load data", JobType.LOAD_DATA, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(loadData, jobManagementService, jobDAO);
		Mockito.when(jobManagementService.loadData(loadData.getData())).thenReturn(true);
		
		jobExecutor.run();
		assertEquals(3, loadData.getJobState().size());
		assertEquals(loadData.getJobState().get(2), JobState.SUCCESS);
	}
	
	@Test
	public void LoadDataJobExecute_Failed() throws InterruptedException {
		JobInfo loadData = new JobInfo("load data", JobType.LOAD_DATA, PriorityLevel.LOW);
		JobManagementExecutor jobExecutor = new JobManagementExecutor(loadData, jobManagementService, jobDAO);
		
		jobExecutor.run();
		assertEquals(3, loadData.getJobState().size());
		assertEquals(loadData.getJobState().get(2), JobState.FAILED);
	}
}
