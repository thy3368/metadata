# UI metadata provider
A UI metadata provider is a library to build a metadata driven UI.

## Description
This approach is especially useful in project teams with a high database competence rather than UI.

In general provides an element alignment by invocation of a single endpoint which provides all data required like 
cardinality, language, font size, and font itself.

## Project structure
```text
- core - aggregate module for all other modules
   - metadata-deploy - hold a liquibase deployment scripts for a base metadata schema
   - metadata-selector - main metadata provider library to be added as a Maven dependency to implementation projects
- ref-impl - sample modules to easier get started
   - metadata-deployer - database schema deployment Spring Boot application
   - metadata-provider-app - metadata provisioning main application which demonstrates a common implementation scenario
```