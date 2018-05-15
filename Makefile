.PHONY: all
all: top swagger

.PHONY: top
top:
	rm -rf $(CURDIR)/swagger-generated
	mvn clean compile

.PHONY: swagger
swagger:
	cd $(CURDIR)/swagger-generated; \
	mvn package
