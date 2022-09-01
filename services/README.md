# Challenge with Java BE

This is a simple project with a challenge for Backend developrs.

### In this project you will learn:

* Consuming API (Feign)
* Creating API REST (Spring Web)
* Create a database (Spring Data)
* Create a CircuitBreak (Resilience4J)
* Different Roles (Spring Security)
* Cache with Hazelcast
* Swagger
* Sonar
* CI/CD
* Docker
* Kubernetes
* Deploy in Cloud

### Tasks: 


Consume the IMDB API (https://imdb-api.com/) and create these features:

- [X] Create the authentication using JWT
- [X] Create two roles ADMIN, USER
- [X] Create an endpoint to return the JWT (5 minutes of timeout), receive the username and password
- [X] Create an endpoint to consume an API from IMDB and populate your database (you can use WebClient or Feign to consume the API). This API could be called just by the ADMIN user, see @Secured and Roles in Spring Security.
- [X] Create an endpoint to create a user, this user will have just a USER role. (The user should have a username and a password)
- [X] Create an endpoint to update the permission to this user to ADMIN and/or USER role. This API could be called just by the ADMIN user, see @Secured and Roles in Spring Security.
- [X] Create an endpoint to list all the movies.
- [X] Create an endpoint to include a movie to the user (favorite list)
- [X] Create an endpoint to exclude the movie from the favorite list
- [X] Each time the user includes the movie in the favorite list add one "star" to the movie
- [X] Create an endpoint to list the top 10 movies, and the movies with more stars.
- [X] Create an endpoint to list the favorite movies per user.
- [ ]  Don't forget to include Swagger, and the test.


----------

Challenges LVL 1:

- [ ] Include this rank top movies in the cache (Hazelcast), and get from it using RateLimiter (https://resilience4j.readme.io/docs/ratelimiter) as failover. 
- [ ]  Publish your project in the Cloud with Heroku.
- [ ]  Find other API to get Movies, and update the first endpoint to use template method design pattern to be able to get the movies from bove APIs. Use a CircuitBreak for that. If you have any problem with one API you should get from the other API as a failover. (You can try that changing the API Key)


----------

Challenges LVL 2:


- [ ] Run your application using Docker, create a docker file.
- [ ] Include Spring Actuator.
- [ ] Create the files to deploy the application using kubernetes.
- [ ] Include the probes from actuator in your deployment.yml
- [ ] Do the deployment into sandbox Openshift (https://developers.redhat.com/developer-sandbox)


### References

* WebClient: https://www.baeldung.com/spring-5-webclient
* Feign: https://www.baeldung.com/intro-to-feign
* Hazelcast: https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-hazelcast.html
* Failover: https://medium.com/lydtech-consulting/failover-and-circuit-breaker-with-resilience4j-14a57a43c0da
