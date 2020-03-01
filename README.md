## Run with docker
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
