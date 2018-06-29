PORT = 32784
IMAGE = scoreboard:v1.0.0
CHART = chart/scoreboard

all: build docker

.PHONY: clean
clean:
	mvn clean

.PHONY: build
build:
	mvn package

.PHONY: docker
docker:
	docker build -t $(IMAGE) .

.PHONY: verify
verify:
	mvn verify

.PHONY: coverage
coverage:
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package com.gavinmogan:codacy-maven-plugin:coverage \
		-DcoverageReportFile=target/site/jacoco-ut/jacoco.xml \
		-DprojectToken=$(CODACY_PROJECT_TOKEN) -DapiToken=$(CODACY_API_TOKEN)

.PHONY: run
run:
	docker run --rm -p$(PORT):9080 $(IMAGE)
	
.PHONY: install
install:
	helm dependency build $(CHART)
	helm upgrade --wait --install scoreboard $(CHART)
