package com.jobdata.services;

public interface JobManagementService {
    boolean sendEmail(String data);

    boolean loadData(String data);

    boolean indexFiles(String data);
}
