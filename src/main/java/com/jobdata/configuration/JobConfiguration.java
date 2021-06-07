package com.jobdata.configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jobdata.models.JobInfo;
import com.jobdata.services.JobManagementExecutor;

@Configuration
@EnableScheduling
public class JobConfiguration{
	
	public int getNumberOfCore() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	@Bean
	public ScheduledExecutorService getExecutorService() {
		return Executors.newScheduledThreadPool(getNumberOfCore());
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public JobManagementExecutor jobManagementExecutor(JobInfo jobInfo) {
		return new JobManagementExecutor(jobInfo);
	}
	
	@Bean
	public Map<UUID, JobInfo> getJobDetails() {
		final Map<UUID, JobInfo> jobDB = new ConcurrentHashMap<>();
		return jobDB;
	}
	
	
}
