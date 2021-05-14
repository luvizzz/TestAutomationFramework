# TestAutomationFramework

How to launch application:
- Start a docker image of postgres locally
-- docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres
- Create a new schema named 'sut'
- Run Java application. All tables will be created automatically.
- If you want to manually populate database, please use file 'sut/src/main/resources/data.sql'

mvn package && java -jar target/sut-1.0-SNAPSHOT.jar
docker build -t sut-1.0 -f src/main/resources/Dockerfile .
docker run -p 8080:8080 -e SPRING.DATASOURCE.URL="jdbc:postgresql://host.docker.internal:5432/postgres" sut-1.0
