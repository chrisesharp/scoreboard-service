PORT = 32784
IMAGE = leaderboard

all: build docker

.PHONY: build
build:
	mvn package

.PHONY: docker
docker:
	docker build -t $(IMAGE) .

.PHONY: run
run:
	docker run --rm -p$(PORT):9080 $(IMAGE)