package com.jobdata.service;

import com.jobdata.models.JobInfo;

public interface JobManagementService {
    boolean sendEmail(String data);

    boolean loadData(String data);

    boolean indexFiles(String data);
    
}
