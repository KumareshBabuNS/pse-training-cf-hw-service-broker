****Hello World Service Broker****


**Sample interactions (using curl)**

*create a hello world service instance*

    $ curl http://admin:admin@localhost:8082/v2/service_instances/foo -d '{
      "service_id":        "helloworld",
      "plan_id":           "hello-world",
      "organization_guid": "org-guid-here",
      "space_guid":        "space-guid-here"
    }' -X PUT -H "Content-Type:application/json"
    > > > > > {"dashboard_url":"dash_url"}

*bind the service instance to an application (guid)*

    $ curl http://admin:admin@localhost:8082/v2/service_instances/foo/service_bindings/mybinding -d '{
       "service_id": "helloworld",
       "plan_id":    "hello-world",
       "app_guid":   "my-application-guid"
     }' -X PUT -H "Content-Type:application/json"
     > > > > {"credentials":{"port":"8080","username":"gnidnibym","host":"localhost","uri":"http://gnidnibym:3e71d@localhost:8080/helloworld/foo","password":"3e71d"},"syslog_drain_url":null}

*the provided url (with embedded credentials) should now give access to the hello world service instance 'foo'*

    $ curl http://gnidnibym:3e71d@localhost:8080/helloworld/foo

    Hello World [foo]

***for more info see: Hello World Service Broker Lab slide deck***
