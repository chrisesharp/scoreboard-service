PORT = 32784
IMAGE = leaderboard:v1.0.0
CHART = chart/leaderboard

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
	helm dependency build $(CHART)
	helm upgrade --wait --install leaderboard $(CHART)
