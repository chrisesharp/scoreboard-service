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

