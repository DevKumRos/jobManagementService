package com.jobdata.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdata.dao.JobDAO;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;
import com.jobdata.queue.JobManagementQueue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobManagementRestTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	MockMvc mvc; 
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@MockBean
	JobManagementQueue jobManagementQueue;
	
	@MockBean
    private JobDAO jobDAO;
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	void postJob_withBody() throws Exception {
		
		JobInfo loadData = new JobInfo("load data", JobType.LOAD_DATA, PriorityLevel.LOW);
		
		String jobStr = objectMapper.writeValueAsString(loadData);
		//Mockito.when(libraryEventsProducer.sendLibraryEvent_Approach2(libraryEvent)).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/jobs/addjobtoqueue").contentType(MediaType.APPLICATION_JSON).
				content(jobStr)).andExpect(status().isOk());
		
	}
	
	@Test
	void postJob_withoutBody() throws Exception {
		
		JobInfo loadData = new JobInfo(null, JobType.LOAD_DATA, PriorityLevel.LOW);
		String exceptedError = "Field: data - Please enter proper data";
		String jobStr = objectMapper.writeValueAsString(loadData);
		//Mockito.when(libraryEventsProducer.sendLibraryEvent_Approach2(libraryEvent)).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders.post("/jobs/addjobtoqueue").contentType(MediaType.APPLICATION_JSON).
				content(jobStr)).andExpect(status().isBadRequest()).
		andExpect(content().string(exceptedError));
		
	}
	
	@Test
	void getAllJob() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/jobs/getAllJobs").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
}
