# Benchmark for MarkovShield

## Prerequisites
You will need `sbt` installed on your system from where you are running these load tests.

## Setup
Before you run the load tests configure the URL of your site in `/src/test/resources/application.properties`.

Example:
```bash
baseUrl=https://markovshield.example.com
```

## Run simulations
Enter a SBT console:
```bash
sbt -Djsse.enableSNIExtension=false
```
**Note**: SSL cert checks need to be disabled because of the MarkovShield demo application uses a self signed certificate.

Now you have two options:
1. Run all tests (simulations):
```bash
gatling:test
```
2. Run a single test (simulation):
```bash
gatling:testOnly NormalUsageSimulation1
```

## View results
Open the `index.html` file inside the `/target/gatling/*` directory.