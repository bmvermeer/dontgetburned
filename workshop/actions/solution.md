# Solution Fix for ACTIONS Assignment

A possible solution for your GitHub action.

- the jobs `opensource-security` and `code-security` only run if the build succeeds
- the job `opensource-monitor` monitors your dependency tree in the Snyk UI
- the job `opensource-monitor` only runs if both `opensource-security` and `code-security` succeed.
- you can use the `continue-on-error: true` if you do not want to block your pipeline



```yaml
name: Java CI with Maven and Snyk

on:
  push:
    branches: [ "master" ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: maven
      - name: Build with Maven
        run: mvn -B package

  opensource-security:
    needs: [ build ]
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@master
      - name: Run Snyk to check for vulnerabilities
        uses: snyk/actions/maven@master
        #        continue-on-error: true
        env:
          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}

  code-security:
    needs: [ build ]
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@master
      - name: Run Snyk to check for vulnerabilities
        uses: snyk/actions/maven@master
        #        continue-on-error: true
        env:
          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}
        with:
          command: code test

  opensource-monitor:
    needs: [ opensource-security, code-security ]
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@master
      - name: Run Snyk to check for vulnerabilities
        uses: snyk/actions/maven@master
        env:
          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}
        with:
          command: monitor



```
