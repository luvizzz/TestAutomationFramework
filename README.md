# TestAutomationFramework

How to launch application:
- Start a docker image of postgres locally
-- docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres
- Create a new schema named 'sut'
- Run Java application. All tables will be created automatically.
- If you want to manually populate database, please use file 'sut/src/main/resources/data.sql'
