package com.jobdata.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobdata.models.JobInfo;
import com.jobdata.queue.JobManagementQueue;
import com.jobdata.services.JobManagmentProccesor;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jobs")
public class JobManagementRest {
	
	@Autowired
	JobManagementQueue<String> jobManagementQueue;
	
	@Autowired
	private JobManagmentProccesor processor;
	
	@PostMapping("/addjobtoqueue")
	public Mono<ResponseEntity<String>> addJobToQueue(@RequestBody JobInfo<String> jobInfo) throws InterruptedException{
		jobManagementQueue.addJobToQueue(jobInfo);
		return Mono.just(new ResponseEntity("Job added to queue successfully", HttpStatus.OK));
	}
	
}
