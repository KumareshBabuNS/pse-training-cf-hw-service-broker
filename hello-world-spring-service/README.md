****spring-boot-hello-world-service****

*request samples using curl*

    curl http://admin@localhost:8080/accounts
    curl http://admin@localhost:8080/authorizations
    curl http://admin@localhost:8080/hwinstances

*add account*

    curl http://admin@localhost:8080/accounts/eddie?password=secret -X PUT


*add hwinstance*

    curl http://admin@localhost:8080/hwinstances/hw1 -X PUT

*add authorization*

    curl http://admin:admin@localhost:8080/authorizations/auth1?accountId=eddie\&hwInstanceId=hw1 -X PUT

*browse to your resource*

    curl http://eddie:secret@localhost:8080/helloworld/hw1

***for more info see: Hello World Service Broker Lab slide deck***