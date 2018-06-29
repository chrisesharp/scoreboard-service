[![Build Status](https://travis-ci.org/chrisesharp/scoreboard-service.svg?branch=master)](https://travis-ci.org/chrisesharp/scoreboard-service)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/81212ba902b34f80b54b806a1968e196)](https://www.codacy.com/app/chrisesharp/scoreboard-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=chrisesharp/scoreboard-service&amp;utm_campaign=Badge_Grade)
# scoreboard Service

## Build

Perform a Maven and Docker build with:
```
make
```

## Verify

Verify the service still honours its `pact` with:
```
make verify
```

## Run

Run locally under Docker with:
```
make run
```

Run under Kubernetes in Docker for Mac with Helm installed:
```
make install
```

## Clean Up

Clean up build artifacts with:
```
make clean
```

