#!/bin/bash

create_docker_network() {
	scoreboard_network_id=`docker network list --filter 'name=scoreboard-net' -q`
	if [ -z ${scoreboard_network_id} ]; then 
		docker network create scoreboard-net || { echo 'Failed to create docker network' ; exit 1; }
	fi
}

add_scoreboard_to_network() {
	scoreboard_container_id=`docker ps -f 'ancestor=scoreboard:v1.0.0' -q`
	if [ -z ${scoreboard_container_id} ]; then 
		echo 'Scoreboard container is not running'
		exit 2
	fi
	
	scoreboard_network_inspect_output=`docker network inspect scoreboard-net`
	if [[ $scoreboard_network_inspect_output != *"$scoreboard_container_id"* ]]; then
		docker network connect --alias scoreboard scoreboard-net $scoreboard_container_id
	fi
}

start_prometheus() {
	if [ -z $SCOREBOARD_JWT ]; then
		echo 'SCOREBOARD_JWT environment variable is not set'
		exit 3
	fi

	echo "scrape_configs:
  - job_name: 'local'

    # Override the global default and scrape targets from this job every 5 seconds.
    scrape_interval: 5s
    scheme: 'https'
    bearer_token: '$SCOREBOARD_JWT'
    tls_config:
      insecure_skip_verify: true

    static_configs:
      - targets: ['scoreboard:9443'] " > $PWD/prometheus_config.yml
	docker run -p 9090:9090 --network scoreboard-net -v $PWD/prometheus_config.yml:/etc/prometheus/prometheus.yml prom/prometheus
}

create_docker_network
add_scoreboard_to_network
start_prometheus
