PORT = 32784
IMAGE = leaderboard

all: build docker deploy

.PHONY: build
build:
	mvn package

.PHONY: docker
docker:
	docker build -t $(IMAGE) .

.PHONY: run
run:
	docker run --rm -p$(PORT):9080 $(IMAGE)
	
.PHONY: deploy
deploy:
	docker run --rm -d -p$(PORT):9080 $(IMAGE)