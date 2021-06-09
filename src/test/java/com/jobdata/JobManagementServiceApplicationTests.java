package com.jobdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdata.models.JobInfo;
import com.jobdata.models.JobType;
import com.jobdata.models.PriorityLevel;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JobManagementServiceApplicationTests {

	@Autowired
	TestRestTemplate restTemplate; 
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void postJOB() {
		JobInfo loadData = new JobInfo("load data", JobType.LOAD_DATA, PriorityLevel.LOW);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JobInfo> httpEntity = new HttpEntity<JobInfo>(loadData,headers);
		ResponseEntity<String> reponseEntity = restTemplate.exchange("/jobs/addjobtoqueue",
				HttpMethod.POST, httpEntity, String.class);
		
		assertEquals(HttpStatus.OK, reponseEntity.getStatusCode());
	}
	
	@Test
	public void getAllJobs() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<JobInfo> httpEntity = new HttpEntity<JobInfo>(null,headers);
		ResponseEntity<Map> reponseEntity = restTemplate.exchange("/jobs/getAllJobs",
				HttpMethod.GET, httpEntity, Map.class);
		
		assertEquals(HttpStatus.OK, reponseEntity.getStatusCode());
	}

}
