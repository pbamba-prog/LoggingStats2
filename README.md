# Spring Boot REST API for Log Ingestion and Statistics with H2 Database

This is a small POC application for log data ingesting application built using Spring Boot REST API with H2 for a database. The application also provides a few rolled up statistics based on uploaded log files. 

This solution took approximately 8 hours for conceptualization, development and documentation.

#### !!! TODO:  FULL TESTS !!!

# Getting Started

To test out this application, you need Maven to build the dependencies.

- First, install the dependencies

```sh
mvn clean install
```
### Running
- Second, run the production build with live reload
```sh
mvn spring-boot:run
```
When the application is first built, it will create a database file in the directory specified in the ```application.properties``` file. 

### Endpoints for Testing

The Log Upload API lives at the route ```/logs```. 
If your application is running on localhost:8080, you would access the API via http://localhost:8080/logs.
```Note: Log data can only be uploaded individually.```
#### Upload Log API: 
```sh
curl -X POST 'http://localhost:8080/logs' -H 'Content-Type: application/json' \
-d '{
"timestamp": "2020-11-09T21:12:29.109+00:00",
"version": 1,
"message": "100.72.22.41 \"GET /applications/ksrouter.euwest1.himalayas/serverGroups/titusprodvpc/eu-west-1/ksrouter.euwest1.himalayas-19_tm-v013?includeDetails=false HTTP/1.1\" 200 9617",
"requestDuration": "33ms",
"sourceIp": "100.72.22.41",
"requestMethod": "GET",
"requestEndpoint":
"/applications/ksrouter.euwest1.himalayas/serverGroups/titusprodvpc/eu-west-1/ksrouter.euwest1.himalayas-19_tm-v013?includeDetails=false",
"requestProtocol": "HTTP/1.1",
"requestUser": "example@netflix.com",
"responseStatus": 200,
"responseSize": 9617
}

#####Success Ratio
curl -X GET 'http://localhost:8080//api/statistics/successRatio?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109'
#####Success Ratio Grouped By User
curl -X GET 'http://localhost:8080//api/statistics/successRatio?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109&groupBy=user'
#####Top Request URLs
curl -X GET 'http://localhost:8080/api/statistics/topRequestUrls?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109'
#####Top Request URLs Grouped By User
curl -X GET 'http://localhost:8080/api/statistics/topRequestUrls?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109&groupBy=user'
#####Average Request Duration
curl -X GET 'http://localhost:8080/api/statistics/avgRequestDuration?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109'
#####Average Request Duration Grouped By User
curl -X GET 'http://localhost:8080/api/statistics/avgRequestDuration?from=2020-11-09%2016:12:29.109&to=2020-11-09%2016:12:29.109&groupBy=user'
```

### Major Design Decisions
1. Choice of Database 

The major choices that made sense were either relational or a document/search oriented database like MongoDB, ElasticSearch, etc.  However for the purposes of this demo, H2 was a reasonable choice since it comes with Java and didn't require any additional component to be installed.

2. Database table design

Since the log records are not nested, it was straightforward to translate the record keys to table columns.  I also did not see a need to normalize.

### Solution Performance
Cold start first insert ~ 300ms
Subsequent inserts ~ 15-20 ms 

Average Request Duration API ~ 15-20 ms for 5 log records
Top Request URLs ~ 15-20 ms for 5 log records
Success Ratio ~ 15-30 ms for 5 log records

Performance Details on Success Ratio API:
Overall Postman response takes 15-20 ms.
Time for same success ratio query from H2 DB console ~ 1-2 ms which tells me that the DB query is not taking long. 
Upon further inspection I found that most the time is spent on network initialization etc. 

[network profile](PostmanNetworkProfile.PNG)

### Scope Reduction
1. Removed "@" from timestamp and version from the Example Log Record for easier JSON Mapping on load API
2. Skipped support for Bulk Upload

### To make this API production ready we will need to look into the following aspects
1. Scalability
    1. Multiple instances of the service behind a load balancer.
1. Performance
    1. Use NoSQL DB like MongoDB, ElasticSearch for long term log data storage.
    1. Tuning the DB
1. Operations
    1. Add monitoring/tracing/alerting for example: tracking API response times, error tracking by type etc. 
    1. Availability/Uptime tracking
    1. API call volume tracking by user
1. Deployment
    1. Add CI/CD with programmatic unit and integration testing
1. Documentation
    1. Add OpenAPI for API documentation
    2. Architecture documentation
    3. Test Case Documentation
1. Security
    1. Add authentication for the API
    2. Throttling 

### Notes
1. Add preprocess/scrubbing logic of the log data to handle @ character in timestamp and version keys in JSON
1. Create response objects for different request types

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.2/maven-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.2/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

