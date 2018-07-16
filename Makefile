PORT = 32784
SSL_PORT = 32443
IMAGE = scoreboard:v1.0.0
CHART = chart/scoreboard
SECURITY = /opt/ibm/wlp/usr/servers/defaultServer/resources/security

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

.PHONY: test
test:
	mvn verify
	mvn liberty:stop-server

.PHONY: coverage
coverage:
	mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package
	mvn com.gavinmogan:codacy-maven-plugin:coverage \
		-DcoverageReportFile=scoreboardService/target/site/jacoco/jacoco.xml \
		-DprojectToken=$(CODACY_PROJECT_TOKEN) -DapiToken=$(CODACY_API_TOKEN)

.PHONY: run
run:
	docker run --rm -p$(PORT):9080 -p$(SSL_PORT):9443 $(IMAGE)

.PHONY: run-keystore
run-keystore:
	docker run --rm \
	 		-p$(PORT):9080 \
			-p$(SSL_PORT):9443 \
			-v keystore:$(SECURITY) \
			$(IMAGE)
		
.PHONY: install
install:
	helm dependency build $(CHART)
	helm upgrade --wait --install scoreboard $(CHART)
