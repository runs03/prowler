version: "3"
services:
  prowler-service:
    build: .
    environment:
      CATALINA_OUT: /dev/stdout
    ports:
    - "8080:8080"