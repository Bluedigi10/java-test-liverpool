# java-test-liverpool
Proyecto para evaluación tecnica

Render deploy customer-service:
- Language: Docker
- Root Directory: customer-service
- Dockerfile Path: Dockerfile
- Docker Context: .
- Health Check Path: /actuator/health
- Required env vars:
  - SPRING_DATASOURCE_URL
  - SPRING_DATASOURCE_USERNAME
  - SPRING_DATASOURCE_PASSWORD
  - PORT
Optional env vars:
  - SPRING_JPA_HIBERNATE_DDL_AUTO
  - SPRING_JPA_SHOW_SQL
  - SPRING_JPA_FORMAT_SQL