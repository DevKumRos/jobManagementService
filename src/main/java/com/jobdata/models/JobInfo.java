package com.jobdata.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.jobdata.services.JobManagementService;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JobInfo implements Serializable {
    private static final long serialVersionUID = 8256726251492277771L;
    
    @Autowired
    private JobManagementService jobManagementService;

    private UUID jobId;
    @NotNull(message = "Please enter proper data")
    private String data;
    @NotNull(message = "Please provide jobType")
    private JobType jobType;
    @NotNull(message = "Please provide priorityLevel")
    private PriorityLevel priorityLevel;
    private List<JobState> jobState;
    private long delay;
    

    public JobInfo(String data, JobType jobType, PriorityLevel priorityLevel) {
        this.data = data;
        this.jobType = jobType;
        this.priorityLevel = priorityLevel;
     }

    public JobInfo(String data, JobType jobType, PriorityLevel priorityLevel, long delay) {
        this.data = data;
        this.jobType = jobType;
        this.priorityLevel = priorityLevel;
        this.jobState = new ArrayList<>();
        this.jobState.add(JobState.QUEUED);
        this.delay = delay;
    }
    
    {
    	this.jobState = new ArrayList<>();
        this.jobState.add(JobState.QUEUED);
        this.delay = 0;
    }
    
   
   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobInfo jobInfo = (JobInfo) o;

        if (delay != jobInfo.delay) return false;
        if (!data.equals(jobInfo.data)) return false;
        if (jobType != jobInfo.jobType) return false;
        if (priorityLevel != jobInfo.priorityLevel) return false;
        return jobState == jobInfo.jobState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, jobType, priorityLevel, jobState, delay);
    }
}
