package com.jobdata.rest;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	JobManagementQueue jobManagementQueue;
	
	@Autowired
	private JobManagmentProccesor processor;
	
	@Autowired
    private Map<UUID, JobInfo> jobDB;
	
	@PostMapping("/addjobtoqueue")
	public Mono<ResponseEntity<String>> addJobToQueue(@Valid @RequestBody JobInfo jobInfo) throws InterruptedException{
		jobManagementQueue.addJobToQueue(jobInfo);
		return Mono.just(new ResponseEntity("Job added to queue successfully", HttpStatus.OK));
	}
	
	@GetMapping("/getAllJobs")
	public ResponseEntity<Map<UUID, JobInfo>> getAllJobs() {
		return new ResponseEntity<>(jobDB, HttpStatus.OK);
	}
	
}
