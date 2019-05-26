# dropwizard-playground

This is a PoC project to work with Drop-Wizard as an alternative to Spring.

## Requirements
- Java 8 or later
- Docker

## Dependencies

You're going to need a Postgres instance. You can lunch it via Docker using:
```
docker run --name dropwizard-sample -e POSTGRES\_USER=dwuser01 -e POSTGRES\_PASSWORD=password -e POSTGRES\_DB=product-db -p 5432:5432 -d postgres
```

## Gradle tasks
```./gradlew fatJar``` Create a fatjar with the dependencies

```./gradlew dbMigrate``` Create the database structure applying the changes defined in the Liquibase script

```./gradlew run``` Start the Jetty server and run the application applying the configuration file defined in the root directory
