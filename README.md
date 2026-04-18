# java-test-liverpool
Proyecto para evaluación tecnica

Render deploy customer-service:
- Language: Docker
- Root Directory: customer-service
- Dockerfile Path: customer-service/Dockerfile
- Health Check Path: /actuator/health
- Required env vars:
  - SPRING_DATASOURCE_URL
  - SPRING_DATASOURCE_USERNAME
  - SPRING_DATASOURCE_PASSWORD
  - PORT