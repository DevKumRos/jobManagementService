# jobManagementService

This application expose 2 endpoint :
# 1.To add job to queue - POST Call
  http://localhost:9090/jobs/addjobtoqueue
  contentType: application/json
  body: {
    "data": "Send Load data with low - 1s",
    "jobType": "LOAD_DATA",
    "priorityLevel": "LOW",
    "delay": 1000
}

