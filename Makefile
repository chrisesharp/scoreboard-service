PORT = 32784
IMAGE = leaderboard:v1.0.0
CHART = chart/leaderboard

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

.PHONY: run
run:
	docker run --rm -p$(PORT):9080 $(IMAGE)
	
.PHONY: install
install:
	helm dependency build $(CHART)
	helm upgrade --wait --install leaderboard $(CHART)
