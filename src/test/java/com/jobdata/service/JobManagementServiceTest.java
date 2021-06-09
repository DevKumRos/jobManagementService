package com.jobdata.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JobManagementServiceTest {
	
	@InjectMocks
	JobManagementServiceImpl jobManagementService; 
	
	
	@Test
    public void testSendEmail_Success() throws InterruptedException {
		boolean status = jobManagementService.sendEmail("send Email");
        assertTrue(status);
    }
	
	@Test
    public void testSendEmail_Failure() throws InterruptedException {
		boolean status = jobManagementService.sendEmail(null);
        assertFalse(status);
    }
	
	@Test
    public void testLoadData_Success() throws InterruptedException {
		boolean status = jobManagementService.loadData("Load Data");
        assertTrue(status);
    }
	
	@Test
    public void testLoadData_Failure() throws InterruptedException {
		boolean status = jobManagementService.loadData(null);
        assertFalse(status);
    }
	
	@Test
    public void testIndexFiles_Success() throws InterruptedException {
		boolean status = jobManagementService.indexFiles("Indexes Files");
        assertTrue(status);
    }
	
	@Test
    public void testIndexFiles_Failure() throws InterruptedException {
		boolean status = jobManagementService.indexFiles(null);
        assertFalse(status);
    }
}
