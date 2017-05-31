# Benchmark for MarkovShield

## Run simulations
Enter a SBT console:
```bash
sbt -Djsse.enableSNIExtension=false
```
**Note**: SSL cert checks need to be disabled because of the MarkovShield demo application uses a self signed certificate.

Now yoou have to options:
1. Run all tests (simulations):
```bash
gatling:test
```
2. Run a single test (simulation):
```bash
gatling:testOnly LoadSimulation1
```

## View results
Open the `index.html` inside the `/target/gatling/loadsimulation*` directories.