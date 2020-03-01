## FileProcessorAPI
   User this Spring Boot API to parse CSV and XML files
   
   - This API imports the passed file
   - Validates the transactions
   - Checks if the imported transactions are valid
   - And Finally returns the list of imported transactions, list of violations (if any) 
     and the transactions balance status as a response. 
  
## Quick Start Run with docker
##### Create env file at envs/common.env

### Allow exection for script local_run.sh and pull_run.sh
    sudo chmod +x local_run.sh
    sudo chmod +x pull_run.sh

### Run script to build and run image locally
    (Maven must be installed before running the script)
    ./local_run.sh

### Build and a new image from dockerhub
    ./pull_run.sh
    
### Run with docker compose 
    docker-compose up -d --build
    
#### NOTE : All the script files can be downloaded from this repo
 
## Test API
   - After running the application through docker scripts
   - The Application will run in http://localhost:8080
   - There are two end points exposed to parse CSV and XML files
   - To test from a REST Client (like Postman)
   - Perform a POST request with URL http://localhost:8080/processFile/CSVFile and add a .csv file in the request body 
     to parse CSV file
   - Similarly perform a POST request with URL http://localhost:8080/processFile/XMLFile and add a .xml file in the request body 
     to parse XML file
   - You should get a JSON response 
