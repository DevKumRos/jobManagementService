# Job Management Service

## This application expose 2 endpoint :
```sh
 1.To add job to queue - POST Call
 - http://localhost:9090/jobs/addjobtoqueue
```
Header
  - contentType: application/json

Input Request 
```sh
  body: {
    "data": "Send Load data with low - 1s",
    "jobType": "LOAD_DATA",
    "priorityLevel": "LOW",
    "delay": 1000
}
```
JobType: We have 3 type job 
  - LOAD_DATA
  - INDEX_FILES
  - SEND_EMAIL

priorityLevel: We have 2 priority by default it will be LOW
   - LOW
   - HIGH
   
Based on priority task execution will takes place HIGH priority task will always take precedence over LOW priority task.

Delay in request basically tell after, how many millisecond task should execute.

Response
```sh
     Job added to queue successfully
```
```sh
2. Get All Jobs details. - GET CALL
 - http://localhost:9090/jobs/getAllJobs
```

This will give complete information all job in application.
Whether Job is in QUEUE or RUNNING or SUCCESSS or FAILED
```sh
{
    "1a9356a8-3446-4dbb-916e-9d06f8d2947c": {
        "jobManagementService": null,
        "jobId": "1a9356a8-3446-4dbb-916e-9d06f8d2947c",
        "data": "Send Load data - 19s",
        "jobType": "LOAD_DATA",
        "priorityLevel": "LOW",
        "jobState": [
            "QUEUED",
            "RUNNING",
            "SUCCESS"
        ],
        "delay": 19000
    },
    "7b7c6cda-31d0-4d5b-b324-6f6690f1191a": {
        "jobManagementService": null,
        "jobId": "7b7c6cda-31d0-4d5b-b324-6f6690f1191a",
        "data": "Send Load data with High - 8s",
        "jobType": "LOAD_DATA",
        "priorityLevel": "HIGH",
        "jobState": [
            "QUEUED",
            "RUNNING",
            "SUCCESS"
        ],
        "delay": 8000
    },
    "ed383775-9f8d-4518-b3b7-c9cc2c43e876": {
        "jobManagementService": null,
        "jobId": "ed383775-9f8d-4518-b3b7-c9cc2c43e876",
        "data": "Send Files Index - 15s",
        "jobType": "INDEX_FILES",
        "priorityLevel": "LOW",
        "jobState": [
            "QUEUED"
        ],
        "delay": 15000
    },
    "a6d57fa8-f077-4bfc-8de9-efca59f533f7": {
        "jobManagementService": null,
        "jobId": "a6d57fa8-f077-4bfc-8de9-efca59f533f7",
        "data": "Send Email to empolyee - 18s",
        "jobType": "SEND_EMAIL",
        "priorityLevel": "LOW",
        "jobState": [
            "QUEUED",
            "RUNNING"
        ],
        "delay": 18000
    },
    "a46a9d05-66dc-45fa-877a-9d97eb9c4264": {
        "jobManagementService": null,
        "jobId": "a46a9d05-66dc-45fa-877a-9d97eb9c4264",
        "data": "Send Files Index - 15s",
        "jobType": "INDEX_FILES",
        "priorityLevel": "LOW",
        "jobState": [
            "QUEUED",
            "RUNNING",
            "SUCCESS"
        ],
        "delay": 15000
    },
    "8dd422bf-66f0-4463-b342-8421dc45bc23": {
        "jobManagementService": null,
        "jobId": "8dd422bf-66f0-4463-b342-8421dc45bc23",
        "data": "Send Load data with low - 1s",
        "jobType": "LOAD_DATA",
        "priorityLevel": "LOW",
        "jobState": [
            "QUEUED",
            "RUNNING",
            "SUCCESS"
        ],
        "delay": 1000
    }
}
```

