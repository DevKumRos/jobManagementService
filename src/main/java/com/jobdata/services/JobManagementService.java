package com.jobdata.services;

import com.jobdata.models.JobInfo;

public interface JobManagementService {
    boolean sendEmail(String data);

    boolean loadData(String data);

    boolean indexFiles(String data);
    
    void saveJob(JobInfo jobInfo);
}
