package com.jobdata.dao;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.jobdata.models.JobInfo;

@Component
public class JobDAO {
	private Map<UUID, JobInfo> jobDB= new ConcurrentHashMap<>();
	
	public void saveJob(JobInfo jobInfo) {
		if(jobInfo.getJobId() == null)
			jobInfo.setJobId(UUID.randomUUID());
    	
		jobDB.put(jobInfo.getJobId(), jobInfo);
	}
	
	public Map<UUID, JobInfo> getAllJob() {
		return jobDB;
	}
	
	public int numberofJobInQueue() {
		return jobDB.size();
	}

}
