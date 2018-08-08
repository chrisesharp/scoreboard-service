[![Build Status](https://travis-ci.org/chrisesharp/scoreboard-service.svg?branch=master)](https://travis-ci.org/chrisesharp/scoreboard-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/81212ba902b34f80b54b806a1968e196)](https://www.codacy.com/app/chrisesharp/scoreboard-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=chrisesharp/scoreboard-service&amp;utm_campaign=Badge_Grade)
# scoreboard Service

## Build

Perform a Maven and Docker build with:
```
make
```

## Test

Verify the service still honours its `pact` with:
```
make test
```

## Run

Run locally under Docker with:
```
make run-keystore
```
This will mount the keystore volume you will need to have created using:
https://github.com/chrisesharp/shared-keystore 

Run under Kubernetes in Docker for Mac with Helm installed:
```
make install
```
This will mount the keystore persistent volume in Kubernetes you will have 
needed to deploy using:
https://github.com/chrisesharp/shared-keystore 

To also run prometheus connected to the scoreboard service you first need to obtain a JWT by running the scoreboard login service:
https://github.com/chrisesharp/scoreboard-login-service
And then visiting:
http://localhost:32001/GitHubAuth
Before starting prometheus:

```
export SCOREBOARD_JWT=<YOUR JWT>
make run-prometheus
```

## Remove

To remove the service from your kubernetes, run:
```
make remove
```

## Clean Up

Clean up build artifacts with:
```
make clean
```

