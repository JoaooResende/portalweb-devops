services:
  app:
    build: .
    container_name: portalweb-app
    ports:
      - "8080:8080"
    depends_on:
      - oracle
    environment:
      SPRING_DATASOURCE_URL: jdbc:oracle:thin:@oracle:1521/XEPDB1
      SPRING_DATASOURCE_USERNAME: system
      SPRING_DATASOURCE_PASSWORD: oracle

  oracle:
    image: gvenzl/oracle-xe
    container_name: portalweb-oracle
    ports:
      - "1521:1521"
    environment:
      ORACLE_PASSWORD: oracle
    volumes:
      - oracle-data:/opt/oracle/oradata

volumes:
  oracle-data: