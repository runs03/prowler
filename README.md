## Prowler
There are 3 major components:
1. **Service**: The service component defines the Prowler APIs.
2. **Model**: Defines the models and shapes of the apis used both in client and service.
3. **Agent**: The prowler agent runs on the host application.


## Steps

### 1. Build Binary
```
# Build models
cd <workspace>/prowler/model/model/
mvn clean install

# Build service
cd <workspace>/prowler/service
mvn clean install
```

### 2. Docker
```
cd <workspace>/prowler/service

# Build docker image of the service
docker build -t prowler-app:1.0 . 

# Spin up containers
docker-compose up
```

Make sure there are no active containers and network

```
# List all active or stopped containers
docker container ls -a

# Clean-up the containers
docker rm -f  prowler-app spanner-cli spanner-init spanner 

# List all networks
docker network ls

# Clean lingering networks, default name is service_default
docker network prune # clean up all networks.
```

### 3. Initialize the service
Invoke the initialize api which creates database and tables in spanner. 
Prowler use spanner emulator which is the docker image of spanner.

```
curl -X 'GET' \
'http://localhost:8080/ProwlerApp/apis/initialize' \
-H 'accept: application/json' 
```

http://localhost:8080/ProwlerApp/apis/initialize can also be invoked in browser.

### 4. APIs
**Create Application**
```
curl -X 'POST' \
  'http://localhost:8080/ProwlerApp/apis/applications' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "app1",
  "owner": "owner-email@domain.com",
  "description" : "Test application 1"
}'
```

**Get Application**: Use the appname for fetching the details of the application.
```
curl -X 'GET' \
  'http://localhost:8080/ProwlerApp/apis/applications/app1' \
  -H 'accept: application/json' 
```

**Reporting Violation**:
ViolationId will be returned in the reponse which can be used for fetching the violation details.
```
curl -X 'POST' \
  'http://localhost:8080/ProwlerApp/apis/applications/app1/violations' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "violationId": "v1",
  "violationType": "FINANCIAL",
  "hostname": "host1.google.com",
  "applicationName": "app1",
  "redactedLogLine": "******redacted log line ****",
  "violationTimestamp" : "2023-03-04T17:59:41.664Z"
}'
```

**Get Violation**
```
curl -X 'GET' \
  'http://localhost:8080/ProwlerApp/apis/applications/app1/violations/b0c0de56-9dd2-4848-8139-d7d066ac3b66' \
  -H 'accept: application/json'
```

**Find Violations**: 
Find violations with in a period (start and end) for an application.
```
curl -X 'GET' \
  'http://localhost:8080/ProwlerApp/apis/applications/app1/violations/find?start=2023-03-01T01:59:41.664Z&end=2023-03-06T01:59:41.664Z' \
  -H 'accept: application/json'

```
